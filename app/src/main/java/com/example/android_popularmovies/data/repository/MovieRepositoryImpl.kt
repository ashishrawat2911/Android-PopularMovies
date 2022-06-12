package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.MovieEntityToMovieMapper
import com.example.android_popularmovies.data.MovieToMovieEntityMapper
import com.example.android_popularmovies.data.source.local.MovieDao
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.model.Movie
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import retrofit2.Response

class MovieRepositoryImpl(
    private val service: MovieApiService,
    private val movieDao: MovieDao,
    private val movieToMovieEntityMapper: MovieToMovieEntityMapper,
    private val movieEntityToMovieMapper: MovieEntityToMovieMapper
) : MovieRepository {

    override fun loadMovies(): Single<MovieListModel> {
        return service.popularMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): Response<Movie> {
        return service.movieDetails(movieId);
    }

    override suspend fun getMovieBelongings(movieId: Int): Response<MovieBelongingList> {
        return service.movieBelongings(movieId);
    }

    override suspend fun cacheMovie(movies: List<Movie>) {
        movieDao.addMovies(movies.map { movieToMovieEntityMapper.mapFromModel(model = it) })
    }

    override suspend fun getCacheMovies(): List<Movie> {
        return movieDao.getMovies().map { movieEntityToMovieMapper.mapFromModel(it) };
    }

    override suspend fun getCacheMovie(id: Int): Movie {
        return movieEntityToMovieMapper.mapFromModel(movieDao.getMovie(id))
    }
}