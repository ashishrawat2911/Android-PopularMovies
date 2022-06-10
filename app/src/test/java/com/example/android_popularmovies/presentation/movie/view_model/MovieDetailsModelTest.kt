package com.example.android_popularmovies.presentation.movie.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android_popularmovies.data.repository.MockMovies
import com.example.android_popularmovies.data.source.remote.model.Movie
import com.example.android_popularmovies.di.qualifiers.MockMovieRepoQualifier
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MovieDetailsModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    @MockMovieRepoQualifier
    lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var moviesObserver: Observer<ResultState<Movie>>


    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private var isNetworkAvailable: Boolean = false


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        setUpUseCases()
        setUpViewModel()
    }


    private fun setUpUseCases() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    }

    private fun setUpViewModel() {
        movieDetailViewModel = MovieDetailViewModel(
            getMovieDetailsUseCase,
            isNetworkAvailable,
        )

        movieDetailViewModel.state.observeForever(moviesObserver)
    }


    @Test
    fun fetchMoviesDetails_returnsData() {
        val movieDetail = MockMovies.generateMovie()
        CoroutineScope(Dispatchers.IO).launch {
            stubFetchMovies(Response.success(movieDetail))
            movieDetailViewModel.getMovieDetails(0)
        }

        moviesObserver.onChanged(ResultState.Success(movieDetail))
        verify(moviesObserver).onChanged(ResultState.Success(movieDetail))
    }


    private suspend fun stubFetchMovies(movie: Response<Movie>) {
        `when`(getMovieDetailsUseCase.getMovieDetail(0))
            .thenReturn(movie)
    }
}
