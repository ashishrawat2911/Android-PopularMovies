package com.example.android_popularmovies.data.source.remote

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/popular")
    fun popularMovies(): MovieListApiModel

    @GET("movie/{movie_id}")
    suspend fun movieDetails(@Path("movie_id") movieId: Int): MovieApiModel

    @GET("movie/{movie_id}/lists")
    suspend fun movieBelongings(@Path("movie_id") movieId: Int): MovieBelongingList
}
