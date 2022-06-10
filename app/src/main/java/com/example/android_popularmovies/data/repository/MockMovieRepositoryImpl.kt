package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.source.remote.model.Movie
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import retrofit2.Response

class MockMovieRepositoryImpl(
) : MovieRepository {
    override fun loadMovies(): Single<MovieListModel> {
        return Single.just(MockMovies.generateMovieListModel(10))
    }

    override fun cacheMovie(movies: List<Movie>) {

    }

    override fun getCacheMovies(): List<Movie> {
        return MockMovies.generateListOfMovies(10)
    }

    override fun getCacheMovie(id: Int): Movie {
        return MockMovies.generateMovie()
    }

    override suspend fun getMovieDetails(movieId: Int): Response<Movie> {
        return Response.success(MockMovies.generateMovie())
    }

    override suspend fun getMovieBelongings(movieId: Int): Response<MovieBelongingList> {
        return Response.success(MockMovies.generateMovieBelongingList(10))
    }
}