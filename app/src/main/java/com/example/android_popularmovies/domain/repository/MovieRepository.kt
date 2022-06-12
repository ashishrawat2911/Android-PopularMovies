package com.example.android_popularmovies.domain.repository

import com.example.android_popularmovies.data.source.remote.model.Movie
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListModel
import io.reactivex.Single
import retrofit2.Response


interface MovieRepository {
    fun loadMovies(): Single<MovieListModel>
    suspend fun cacheMovie(movies: List<Movie>)
    suspend fun getCacheMovies(): List<Movie>
    suspend fun getCacheMovie(id:Int): Movie
    suspend fun getMovieDetails(movieId: Int): Response<Movie>
    suspend fun getMovieBelongings(movieId: Int): Response<MovieBelongingList>
}
