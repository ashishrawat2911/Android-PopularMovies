package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.mapper.MovieEntityToStateMapper
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.presentation.movie.view_model.impl.MovieDetailViewModelImpl
import com.example.android_popularmovies.utils.AppDispatchers
import com.example.android_popularmovies.utils.MockMovies
import com.example.android_popularmovies.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
    lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetailViewModel: MovieDetailViewModelImpl

    private val movieEntityToStateMapper: MovieEntityToStateMapper = MovieEntityToStateMapper()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieDetailViewModel = MovieDetailViewModelImpl(
            getMovieDetailsUseCase, testDispatcher, movieEntityToStateMapper
        )
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun fetchMoviesDetails_returnsData() = runTest {
        val movieDetail = MockMovies.generateMovieEntity()
        stubFetchMovies(movieDetail)
        movieDetailViewModel.getMovieDetails(0)
        advanceUntilIdle()
        val data = MovieDetailState(
            ResultState.Success(
                movieEntityToStateMapper.map(movieDetail)
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
//        verify(movieRepository).getMovieDetails(0)
        val error = MovieDetailState(
            ResultState.Error("org.mockito.exceptions.base.MockitoException: Error")
        )
        Assert.assertEquals(movieDetailViewModel.detailState.value, error)
    }



    private suspend fun stubFetchMovies(movie: MovieEntity) {
        `when`(getMovieDetailsUseCase(0))
            .thenReturn(flow { emit(movie) })
    }

}
