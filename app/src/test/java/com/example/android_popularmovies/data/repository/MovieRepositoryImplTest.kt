package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
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


        assert(response.results!!.size == 6)
    }

    @Test
    fun testGetMovieDetails_returnData() {
        val mockMovieDetails = MockMovies.generateMovie()
        val response = runBlocking {
            stubMoviesDetails(mockMovieDetails)
            movieApiService.movieDetails(0)
        }
        assert(response.title == mockMovieDetails.title)
    }

    @Test
    fun testGetMovieBelongings_returnData() {
        val mockMovieBelongings = MockMovies.generateMovieBelongingList(10)
        val response = runBlocking {
            stubMoviesBelongings(mockMovieBelongings)
            movieApiService.movieBelongings(0)
        }
        assert(response.results.size == mockMovieBelongings.results.size)
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

    private suspend fun stubMoviesBelongings(movieBelonging: MovieBelongingList) {
        Mockito.`when`(movieApiService.movieBelongings(0)).thenReturn(
            movieBelonging
        )
    }
}
