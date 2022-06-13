package com.example.android_popularmovies.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): Single<List<MovieDbModel>>

    @Query("SELECT * FROM movies where id==:movieId")
    fun getMovie(movieId: Int): MovieDbModel

    @Query("DELETE FROM movies")
    fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<MovieDbModel>)
}
