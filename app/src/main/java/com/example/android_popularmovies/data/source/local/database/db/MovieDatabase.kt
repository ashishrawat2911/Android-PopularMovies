package com.example.android_popularmovies.data.source.local.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_popularmovies.data.source.local.database.dao.MovieDao
import com.example.android_popularmovies.data.source.local.database.model.MovieDbModel
import com.example.android_popularmovies.utils.Constants

@Database(entities = [MovieDbModel::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, MovieDatabase::class.java, Constants.dbName
        ).build()
    }
}
