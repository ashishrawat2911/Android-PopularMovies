package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.utils.MovieTestFactory
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
            val movieDetailsResponse = MovieTestFactory.generateMovieEntity()
            stubMovieDetailsResponse(movieDetailsResponse)
            assert(movieDetailsResponse.title == (repository.getMovieDetails(0) as NetworkResult.Success<MovieDomainModel>).data.title)
        }
    }


    private suspend fun stubMovieDetailsResponse(movieDomainModel: MovieDomainModel) {
        Mockito.`when`(repository.getMovieDetails(0)).thenReturn(NetworkResult.Success(movieDomainModel))
    }
}