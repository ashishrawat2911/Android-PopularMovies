package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.mapper.MovieDetailDomainToStateModel
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
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
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private val movieDetailDomainToStateModel: MovieDetailDomainToStateModel = MovieDetailDomainToStateModel()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailsUseCase, testDispatcher, movieDetailDomainToStateModel
        )
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun fetchMoviesDetails_returnsData() = runTest {
        val movieDetail = MovieTestFactory.generateMovieDetailEntity()
        stubFetchMovies(movieDetail)
        movieDetailViewModel.getMovieDetails(0)
        advanceUntilIdle()
        val data = MovieDetailState.Success(
            movieDetailDomainToStateModel.map(movieDetail)
        )
        Assert.assertEquals(movieDetailViewModel.uiState.value, data)
    }


    private suspend fun stubFetchMovies(movie: MovieDetailDomainModel) {
        `when`(getMovieDetailsUseCase(0))
            .thenReturn(flow { emit(NetworkResult.Success(movie)) })
    }

}
