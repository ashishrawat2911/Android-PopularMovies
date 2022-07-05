package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_popularmovies.AppDispatchers
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    private val dispatcher = StandardTestDispatcher()
    private val testDispatcher = AppDispatchers(
        IO = dispatcher,
        Main = Dispatchers.Unconfined
    )

    @Mock
    @MockMovieRepoQualifier
    lateinit var movieRepository: MovieRepository

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
            testDispatcher
        )
    }

    @Test
    fun fetchMoviesDetails_returnsData() = runTest {
        val movieDetail = MockMovies.generateMovie()
        stubFetchMovies(movieDetail.toEntity())
        movieDetailViewModel.getMovieDetails(0)
        advanceUntilIdle()
        val data = MovieDetailState(
            ResultState.Success(
                movieDetail.toEntity().toState()
            )
        )
        Assert.assertEquals(movieDetailViewModel.detailState.value, data)
    }

    @Test
    @Throws(Exception::class)
    fun fetchMovieDetails_returnsError() = runTest {
        `when`(movieRepository.getMovieDetails(0)).thenThrow(MockitoException("Error"))
        movieDetailViewModel.getMovieDetails(0)
        advanceUntilIdle()
        verify(movieRepository).getMovieDetails(0)
        val error = MovieDetailState(
            ResultState.Error("org.mockito.exceptions.base.MockitoException: Error")
        )
        Assert.assertEquals(movieDetailViewModel.detailState.value, error)
    }

    @Test
    fun fetchMovieBelongings_returnsData() = runTest {
        val movieBelongings =
            MockMovies.generateMovieBelongingList(10).results.map { it.toEntity() }
        stubMovieBelongings(movieBelongings)
        movieDetailViewModel.getMovieBelongings(0)
        advanceUntilIdle()
        verify(movieRepository).getMovieBelongings(0)
        val data = MovieBelongingState(
            ResultState.Success(movieBelongings.map { it.toState() })
        )

        Assert.assertEquals(movieDetailViewModel.belongingState.value, data)
    }

    @Test
    @Throws(Exception::class)
    fun fetchMovieBelongings_returnsError() = runTest {
        `when`(movieRepository.getMovieBelongings(0)).thenThrow(MockitoException("Error"))
        movieDetailViewModel.getMovieBelongings(0)
        advanceUntilIdle()
        verify(movieRepository).getMovieBelongings(0)
        val error = MovieBelongingState(
            ResultState.Error("org.mockito.exceptions.base.MockitoException: Error")
        )
        Assert.assertEquals(movieDetailViewModel.belongingState.value, error)
    }

    private suspend fun stubFetchMovies(movie: MovieEntity) {
        `when`(movieRepository.getMovieDetails(0))
            .thenReturn(movie)
    }

    private suspend fun stubMovieBelongings(movieBelongings: List<MovieBelongingsEntity>) {
        `when`(movieRepository.getMovieBelongings(0)).thenReturn(movieBelongings)
    }
}
