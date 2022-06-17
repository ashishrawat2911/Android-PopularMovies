package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android_popularmovies.AppDispatchers
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.di.qualifiers.MockMovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

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

    @Mock
    private lateinit var moviesObserver: Observer<MovieListState>

    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var movieListViewModel: MovieListViewModel


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
        getMoviesUseCase = GetMoviesUseCase(movieRepository)
    }

    private fun setUpViewModel() {
        runBlocking {
            stubFetchMovies(listOf())
        }
        movieListViewModel = MovieListViewModel(
            getMoviesUseCase,
            testDispatcher
        )

        movieListViewModel.movieState.observeForever(moviesObserver)
    }

    @Test
    fun fetchMoviesList_returnsEmpty() = runTest {
        stubFetchMovies(listOf())
        advanceUntilIdle()
        verify(moviesObserver).onChanged(MovieListState(ResultState.Success(listOf())))
    }

    @Test
    fun fetchMoviesList_returnsData() = runTest {
        val listOfMovies = MockMovies.generateListOfMovies(10).map { it.toEntity() }
        stubFetchMovies(listOfMovies)

        movieListViewModel.fetchMoviesList()
        advanceUntilIdle()

        verify(moviesObserver, times(2)).onChanged(MovieListState(ResultState.Success(listOfMovies.map { it.toState() })))
    }

    @Test
    @Throws(Exception::class)
    fun fetchMoviesList_throwException() = runTest {
        Mockito.`when`(movieRepository.getMovies()).thenThrow(MockitoException("Error"))
        movieListViewModel.fetchMoviesList()
        advanceUntilIdle()
        val error = MovieListState(
            ResultState.Error("org.mockito.exceptions.base.MockitoException: Error")
        )
        verify(moviesObserver,times(2)).onChanged(error)
        Assert.assertEquals(movieListViewModel.movieState.value, error)

    }

    private suspend fun stubFetchMovies(movies: List<MovieEntity>) {
        Mockito.`when`(getMoviesUseCase()).thenReturn(movies)
    }
}
