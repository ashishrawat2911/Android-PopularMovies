package com.example.android_popularmovies.data.source.remote

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/popular")
    suspend fun popularMovies(): MovieListApiModel

    @GET("movisase/{movie_id}")
    suspend fun movieDetails(@Path("movie_id") movieId: Int): MovieApiModel

    @GET("movie/{movie_id}/lists")
    suspend fun movieBelongings(@Path("movie_id") movieId: Int): MovieBelongingList
}
