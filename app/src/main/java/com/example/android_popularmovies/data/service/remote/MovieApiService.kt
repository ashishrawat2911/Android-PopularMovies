package com.example.android_popularmovies.data.source.remote

import com.example.android_popularmovies.data.model.MovieListResponseModel
import com.example.android_popularmovies.data.model.MovieResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/popular")
    suspend fun popularMovies(): MovieListResponseModel

    @GET("movie/{movie_id}")
    suspend fun movieDetails(@Path("movie_id") movieId: Int): MovieResponseModel
}
