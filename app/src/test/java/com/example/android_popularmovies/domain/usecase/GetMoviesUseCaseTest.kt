package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.utils.MockMovies
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
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
        val movies = MockMovies.generateListOfMovieEntity(10)
        stubMoviesResponse(movies)
        val response = repository.getMovies()
        assert(response.size == movies.size)
    }

    private suspend fun stubMoviesResponse(movies: List<MovieEntity>) {
        Mockito.`when`(repository.getMovies()).thenReturn(movies)
    }
}