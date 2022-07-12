package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.store.MovieDataStore
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
    private lateinit var movieDataStore: MovieDataStore


    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(
            movieDataStore,
        )
    }

    @Test
    fun testInvoke_checkTitle() {
        runBlocking {
            val movieDetailsResponse = MockMovies.generateMovieApiModel()
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