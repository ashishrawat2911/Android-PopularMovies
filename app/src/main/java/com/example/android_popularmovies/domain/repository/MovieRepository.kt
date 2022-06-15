package com.example.android_popularmovies.domain.repository

import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity
import io.reactivex.Single

interface MovieRepository {
    fun getMovies(): Single<List<MovieEntity>>
    suspend fun getMovieDetails(movieId: Int): MovieEntity
    suspend fun getMovieBelongings(movieId: Int): List<MovieBelongingsEntity>
}
