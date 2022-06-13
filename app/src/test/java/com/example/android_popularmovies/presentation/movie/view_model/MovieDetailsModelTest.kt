package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.di.qualifiers.MockMovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMovieBelongingsUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MovieDetailsModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Mock
    @MockMovieRepoQualifier
    lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var moviesObserver: Observer<ResultState<MovieEntity>>


    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var getMovieBelongingsUseCase: GetMovieBelongingsUseCase

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private var isNetworkAvailable: Boolean = false


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        setUpUseCases()
        setUpViewModel()
        Dispatchers.setMain(dispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun setUpUseCases() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
        getMovieBelongingsUseCase = GetMovieBelongingsUseCase(movieRepository)
    }

    private fun setUpViewModel() {
        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailsUseCase,
            getMovieBelongingsUseCase
        )

        movieDetailViewModel.state.observeForever(moviesObserver)
    }


    @Test
    fun fetchMoviesDetails_returnsData() {
        val movieDetail = MockMovies.generateMovie()
        CoroutineScope(Dispatchers.IO).launch {
            stubFetchMovies(movieDetail.toEntity())
            movieDetailViewModel.getMovieDetails(0)
        }

        moviesObserver.onChanged(ResultState.Success(movieDetail.toEntity()))
        verify(moviesObserver).onChanged(ResultState.Success(movieDetail.toEntity()))
    }


    private suspend fun stubFetchMovies(movie: MovieEntity) {
        `when`(getMovieDetailsUseCase(0))
            .thenReturn(movie)
    }
}
