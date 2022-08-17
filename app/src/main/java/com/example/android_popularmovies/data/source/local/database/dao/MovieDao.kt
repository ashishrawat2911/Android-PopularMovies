package com.example.android_popularmovies.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_popularmovies.data.source.local.database.model.MovieDbModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieDbModel>

    @Query("SELECT * FROM movies where id==:movieId")
    suspend fun getMovie(movieId: Int): MovieDbModel?

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieDbModel>)
}
