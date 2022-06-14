package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.repository.MovieRepositoryImpl
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesUseCaseTest {
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
            val movies = MockMovies.generateMovieListModel(10)
            stubMoviesResponse(Single.just(movies))
           movieApiService.popularMovies().test().assertValue{
               it.results!!.size==movies.results!!.size
           }
        }
    }


    private  fun stubMoviesResponse(singleMovies: Single<MovieListApiModel>) {
        Mockito.`when`(movieApiService.popularMovies()).thenReturn(singleMovies)
    }
}