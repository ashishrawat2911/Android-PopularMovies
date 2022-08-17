package com.example.android_popularmovies.data.source

import com.example.android_popularmovies.data.source.local.database.model.MovieDbModel

interface MovieLocalDataSource {
   suspend fun getMovies(): List<MovieDbModel>

   suspend fun setMoviesToCache(movies: List<MovieDbModel>)

    suspend fun getMovie(movieId: Int): MovieDbModel?
}

