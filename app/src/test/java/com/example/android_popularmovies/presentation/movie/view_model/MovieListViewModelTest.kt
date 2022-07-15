package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.mapper.MovieEntityToStateMapper
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.view_model.impl.MovieListViewModelImpl
import com.example.android_popularmovies.utils.AppDispatchers
import com.example.android_popularmovies.utils.MockMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
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
    lateinit var movieRepository: MovieRepository
    @Mock
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    private lateinit var movieListViewModel: MovieListViewModelImpl

    private val movieEntityToStateMapper: MovieEntityToStateMapper = MovieEntityToStateMapper()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        movieListViewModel =
            MovieListViewModelImpl(getMoviesUseCase, testDispatcher, movieEntityToStateMapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchMoviesList_returnsEmpty() = runTest {
        stubFetchMovies(listOf())
        movieListViewModel.fetchMoviesList()
        advanceUntilIdle()
        Assert.assertEquals(
            movieListViewModel.movieState.value,
            MovieListState.Success(listOf())
        );
    }

    @Test
    fun fetchMoviesList_returnsData() = runTest {
        val listOfMovies = MockMovies.generateListOfMovieEntity(10)
        stubFetchMovies(listOfMovies)
        movieListViewModel.fetchMoviesList()
        advanceUntilIdle()
        Assert.assertEquals(
            movieListViewModel.movieState.value,
            MovieListState.Success(listOfMovies.map { movieEntityToStateMapper.map(it) })
        );
    }

    private suspend fun stubFetchMovies(movies: List<MovieEntity>) {
        Mockito.`when`(getMoviesUseCase()).thenReturn(flow { emit(movies) })
    }
}
