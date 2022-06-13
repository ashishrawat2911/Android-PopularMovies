package com.example.android_popularmovies.domain.repository

import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.domain.entity.MovieEntity
import io.reactivex.Single
import retrofit2.Response


interface MovieRepository {
    fun getMovies(): Single<List<MovieEntity>>
    suspend fun getMovieDetails(movieId: Int): MovieEntity
    suspend fun getMovieBelongings(movieId: Int): Response<MovieBelongingList>
}
