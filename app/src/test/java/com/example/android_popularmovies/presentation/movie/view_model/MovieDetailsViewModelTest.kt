package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android_popularmovies.analytics.MovieAnalytics
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.di.qualifiers.MockMovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMovieBelongingsUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieBelongingState
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.utils.ResultState
import kotlinx.coroutines.*
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

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Mock
    @MockMovieRepoQualifier
    lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var moviesObserver: Observer<MovieDetailState>
    @Mock
    private lateinit var moviesBelongingsObserver: Observer<MovieBelongingState>

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var getMovieBelongingsUseCase: GetMovieBelongingsUseCase

    @Mock
    private lateinit var movieAnalytics: MovieAnalytics

    private lateinit var movieDetailViewModel: MovieDetailViewModel


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
            getMovieBelongingsUseCase,
            movieAnalytics,
        )

        movieDetailViewModel.detailState.observeForever(moviesObserver)
        movieDetailViewModel.belongingState.observeForever(moviesBelongingsObserver)
    }

    @Test
    fun fetchMoviesDetails_returnsData() {
        val movieDetail = MockMovies.generateMovie()
        runBlocking {
            stubFetchMovies(movieDetail.toEntity())
        }
        movieDetailViewModel.getMovieDetails(0)

        moviesObserver.onChanged(
            MovieDetailState(
                ResultState.Success(
                    movieDetail.toEntity().toState()
                )
            )
        )
        verify(moviesObserver).onChanged(
            MovieDetailState(
                ResultState.Success(
                    movieDetail.toEntity().toState()
                )
            )
        )
    }

    @Test
    fun fetchMovieBelongings_returnsData() {
        val movieBelongings =
            MockMovies.generateMovieBelongingList(10).results.map { it.toEntity() }
        CoroutineScope(Dispatchers.IO).launch {
            stubMovieBelongings(movieBelongings)
        }
        movieDetailViewModel.getMovieDetails(0)

        moviesBelongingsObserver.onChanged(
            MovieBelongingState(
                ResultState.Success(
                    movieBelongings.map { it.toState() }
                )
            )
        )
        verify(moviesBelongingsObserver).onChanged(
            MovieBelongingState(
                ResultState.Success(
                    movieBelongings.map { it.toState() }
                )
            )
        )
    }


    private suspend fun stubFetchMovies(movie: MovieEntity) {
        `when`(movieRepository.getMovieDetails(0))
            .thenReturn(movie)
    }
    private suspend fun stubMovieBelongings(movieBelongings: List<MovieBelongingsEntity>) {
        `when`(movieRepository.getMovieBelongings(0)).thenReturn(movieBelongings)
    }
}
