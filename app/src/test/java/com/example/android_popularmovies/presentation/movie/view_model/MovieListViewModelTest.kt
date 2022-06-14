package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.di.qualifiers.MockMovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.utils.ResultState
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @get:Rule
    var instantExecutorRule: CountingTaskExecutorRule = CountingTaskExecutorRule()

    @Mock
    @MockMovieRepoQualifier
    lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var moviesObserver: Observer<MovieListState>

    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var movieListViewModel: MovieListViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpUseCases() {
        getMoviesUseCase = GetMoviesUseCase(movieRepository)
    }

    private fun setUpViewModel() {
        movieListViewModel = MovieListViewModel(
            getMoviesUseCase,
        )

        movieListViewModel.state.observeForever(moviesObserver)
    }

    @Test
    fun fetchMoviesList_returnsEmpty() {
        val movies: List<MovieEntity> = listOf()
        stubFetchMovies(Single.just(movies))

        movieListViewModel.loadMovies()

        verify(moviesObserver, times(2)).onChanged(MovieListState(ResultState.Success(listOf())))
    }

    @Test
    fun fetchMoviesList_returnsData() {

        val listOfMovies = MockMovies.generateListOfMovies(10).map { it.toEntity() }

        stubFetchMovies(Single.just(listOfMovies))

        movieListViewModel.loadMovies()
        moviesObserver.onChanged(MovieListState(ResultState.Success(listOfMovies.map { it.toState() })))
        verify(moviesObserver).onChanged(MovieListState(ResultState.Success(listOfMovies.map { it.toState() })))
    }

    private fun stubFetchMovies(single: Single<List<MovieEntity>>) {
        `when`(getMoviesUseCase()).thenReturn(single)
    }
}
