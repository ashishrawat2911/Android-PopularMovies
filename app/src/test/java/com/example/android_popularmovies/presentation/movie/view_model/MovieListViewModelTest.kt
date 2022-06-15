package com.example.android_popularmovies.presentation.movie.view_model

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
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

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
        stubFetchMovies(Single.just(listOf()))
        movieListViewModel = MovieListViewModel(
            getMoviesUseCase,
        )

        movieListViewModel.state.observeForever(moviesObserver)
    }

    @Test
    fun fetchMoviesList_returnsEmpty() {
        val movies: List<MovieEntity> = listOf()
        stubFetchMovies(Single.just(movies))
        movieListViewModel.fetchMoviesList()
        moviesObserver.onChanged(MovieListState(ResultState.Success(listOf())))
        verify(moviesObserver).onChanged(MovieListState(ResultState.Success(listOf())))
    }

    @Test
    fun fetchMoviesList_returnsData() {
        val listOfMovies = MockMovies.generateListOfMovies(10).map { it.toEntity() }
        stubFetchMovies(Single.just(listOfMovies))

        movieListViewModel.fetchMoviesList()
        moviesObserver.onChanged(MovieListState(ResultState.Success(listOfMovies.map { it.toState() })))
        verify(moviesObserver).onChanged(MovieListState(ResultState.Success(listOfMovies.map { it.toState() })))
    }

    private fun stubFetchMovies(single: Single<List<MovieEntity>>) {
        Mockito.`when`(getMoviesUseCase()).thenReturn(single.subscribeOn(Schedulers.trampoline()))
    }
}
