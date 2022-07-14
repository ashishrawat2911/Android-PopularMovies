package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseTest {
    @Mock
    private lateinit var repository: MovieRepository

    @Test
    fun testInvoke_checkTitle() {
        runBlocking {
            val movieDetailsResponse = MockMovies.generateMovieEntity()
            stubMovieDetailsResponse(movieDetailsResponse)
            assert(
                movieDetailsResponse.title == repository.getMovieDetails(0).title
            )
        }
    }


    private suspend fun stubMovieDetailsResponse(movieEntity: MovieEntity) {
        Mockito.`when`(repository.getMovieDetails(0)).thenReturn(movieEntity)
    }
}