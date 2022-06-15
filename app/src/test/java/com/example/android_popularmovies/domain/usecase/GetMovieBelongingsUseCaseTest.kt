package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieBelongingsUseCaseTest {
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
    fun testInvoke_checkSize() {
        runBlocking {
            val movieBelongingResponse =
                MockMovies.generateMovieBelongingList(10)
            stubMovieBelongingResponse(movieBelongingResponse)
            val getMovieBelongings = repository.getMovieBelongings(0)

            assert(movieBelongingResponse.results.size == getMovieBelongings.size)
        }
    }

    @Test
    fun testInvoke_checkIndex0Desc() {
        runBlocking {
            val movieBelongingResponse =
                MockMovies.generateMovieBelongingList(10)
            stubMovieBelongingResponse(movieBelongingResponse)
            assert(
                movieBelongingResponse.results[0].description == repository.getMovieBelongings(
                    0
                )[0].description
            )
        }
    }

    private suspend fun stubMovieBelongingResponse(res: MovieBelongingList) {
        Mockito.`when`(movieApiService.movieBelongings(0)).thenReturn(res)
    }
}