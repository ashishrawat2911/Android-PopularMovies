package com.example.android_popularmovies.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.utils.Constants

@Database(entities = [MovieDbModel::class], version = Migrations.DB_VERSION)
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
