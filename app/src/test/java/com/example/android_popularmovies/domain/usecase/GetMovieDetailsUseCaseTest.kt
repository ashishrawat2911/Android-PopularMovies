package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseTest {
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var movieApiService: MovieApiService

    @Mock
    private lateinit var movieDao: MovieDao

    private val isNetworkAvailable: Boolean = true

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(
            movieApiService, movieDao, isNetworkAvailable
        )
    }

    @Test
    fun testInvoke_checkTitle() {
        runBlocking {
            val movieDetailsResponse = MockMovies.generateMovie()
            stubMovieDetailsResponse(movieDetailsResponse)
            assert(
                movieDetailsResponse.title == repository.getMovieDetails(0).title
            )
        }
    }


    private suspend fun stubMovieDetailsResponse(movieDetailsResponse: MovieApiModel) {
        Mockito.`when`(movieApiService.movieDetails(0)).thenReturn(movieDetailsResponse)
    }
}