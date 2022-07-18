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
class GetMoviesUseCaseTest {
    @Mock
    private lateinit var repository: MovieRepository


    @Test
    fun testInvoke_checkSize() = runBlocking {
        val movies = MovieTestFactory.generateListOfMovieEntity(10)
        stubMoviesResponse(movies)
        val response = repository.getMovies()
        assert((response as NetworkResult.Success).data.size == movies.size)
    }

    private suspend fun stubMoviesResponse(movies: List<MovieDomainModel>) {
        Mockito.`when`(repository.getMovies()).thenReturn(NetworkResult.Success(movies))
    }
}