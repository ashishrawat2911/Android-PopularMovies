package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.source.remote.service.MovieApiService
import com.example.android_popularmovies.data.source.remote.service.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.service.model.MovieListApiModel
import com.example.android_popularmovies.utils.MockMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @Mock
    private lateinit var movieApiService: MovieApiService

    @Test
    fun getPopularMovies_Completes(): Unit = runBlocking {
        stubPopularMovies(MockMovies.generateMovieListModel(6))
        movieApiService.popularMovies()
        verify(movieApiService).popularMovies()
    }

    @Test
    fun testLoadMovies_returnData() = runBlocking {
        stubPopularMovies(MockMovies.generateMovieListModel(6))

        val response = movieApiService.popularMovies()


        assert(response.results.size == 6)
    }

    @Test
    fun testGetMovieDetails_returnData() {
        val mockMovieDetails = MockMovies.generateMovieApiModel()
        val response = runBlocking {
            stubMoviesDetails(mockMovieDetails)
            movieApiService.movieDetails(0)
        }
        assert(response.title == mockMovieDetails.title)
    }

    private suspend fun stubPopularMovies(model: MovieListApiModel) {
        Mockito.`when`(movieApiService.popularMovies()).thenReturn(
            model
        )
    }

    private suspend fun stubMoviesDetails(model: MovieApiModel) {
        Mockito.`when`(movieApiService.movieDetails(0)).thenReturn(
            model
        )
    }


}
