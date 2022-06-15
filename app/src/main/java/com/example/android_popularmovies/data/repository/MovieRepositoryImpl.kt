package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieRepositoryImpl(
    private val service: MovieApiService,
    private val movieDao: MovieDao,
    private val isNetworkAvailable: Boolean
) : MovieRepository {

    override fun getMovies(): Single<List<MovieEntity>> {
        return if (isNetworkAvailable) {
            service.popularMovies()
                .flatMap { it ->
                    addMovieToCache(it.results!!.map { it.toEntity() })
                    Single.just(it.results!!)
                }.map { it -> it.map { it.toEntity() } }

        } else {
            movieDao.getMovies().map { it -> it.map { it.toEntity() } }
        }
    }

    private fun addMovieToCache(movies: List<MovieEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.addMovies(movies.map { it.toDBModel() })
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return if (isNetworkAvailable) {
            service.movieDetails(movieId).toEntity()
        } else {
            movieDao.getMovie(movieId).toEntity()
        }
    }

    override suspend fun getMovieBelongings(movieId: Int): List<MovieBelongingsEntity> {
        return service.movieBelongings(movieId).results.map { it.toEntity() }
    }
}
