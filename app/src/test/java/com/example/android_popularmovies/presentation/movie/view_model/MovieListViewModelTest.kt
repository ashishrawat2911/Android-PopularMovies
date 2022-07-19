package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.domain.usecase.FilterMoviesUseCase
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.mapper.MovieDomainToStateModel
import com.example.android_popularmovies.presentation.movie.mapper.MovieStateToDomainModel
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.utils.AppDispatchers
import com.example.android_popularmovies.utils.MovieTestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @Mock
    private lateinit var filterMoviesUseCase: FilterMoviesUseCase

    private lateinit var movieListViewModel: MovieListViewModel

    private val movieDomainToStateMapper: MovieDomainToStateModel = MovieDomainToStateModel()
    private val movieStateToDomainModel: MovieStateToDomainModel = MovieStateToDomainModel()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        movieListViewModel =
            MovieListViewModel(
                getMoviesUseCase,
                filterMoviesUseCase,
                testDispatcher,
                movieDomainToStateMapper,
                movieStateToDomainModel
            )
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
            movieListViewModel.uiState.value,
            MovieListState.Success(listOf())
        )
    }

    @Test
    fun fetchMoviesList_returnsData() = runTest {
        val listOfMovies = MovieTestFactory.generateListOfMovieEntity(10)
        stubFetchMovies(listOfMovies)
        movieListViewModel.fetchMoviesList()
        advanceUntilIdle()
        Assert.assertEquals(
            movieListViewModel.uiState.value,
            MovieListState.Success(listOfMovies.map { movieDomainToStateMapper.map(it) })
        )
    }

    private suspend fun stubFetchMovies(movies: List<MovieDomainModel>) {
        Mockito.`when`(getMoviesUseCase()).thenReturn(flow { emit(NetworkResult.Success(movies)) })
    }
}
