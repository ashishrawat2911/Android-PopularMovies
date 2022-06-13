package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieRepositoryImpl(
    private val service: MovieApiService,
    private val movieDao: MovieDao,
    private val isNetworkAvailable: Boolean
) : MovieRepository {

    override fun getMovies(): Single<List<MovieEntity>> {
        return if (isNetworkAvailable) {
            val movies =
                service.popularMovies().map { it.toEntity() }.flatMap { Single.just(it.results!!) }
            addMovieToCache(movies)
            movies
        } else {
            movieDao.getMovies().map { it -> it.map { it.toEntity() } }
        }
    }

    private fun addMovieToCache(movies: Single<List<MovieEntity>>) {
        movies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                CoroutineScope(Dispatchers.IO).launch {
                    movieDao.addMovies(it.map { it.toDBModel() })
                }
            }.dispose()
    }


    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return if (isNetworkAvailable) {
            val movie = service.movieDetails(movieId).toEntity()
            movie
        } else {
            movieDao.getMovie(movieId).toEntity()
        }
    }

    override suspend fun getMovieBelongings(movieId: Int): Response<MovieBelongingList> {
        return service.movieBelongings(movieId);
    }

}