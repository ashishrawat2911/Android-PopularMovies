package com.example.android_popularmovies.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.repository.mock.MockMovies
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    private lateinit var mDatabase: MovieDatabase
    private val movies = MockMovies.generateListOfMovies(10)

    @Before
    fun createDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        mDatabase.movieDao().addMovies(
            movies.map {
                it.toEntity().toDBModel()
            }
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    fun getMovies() = runBlocking {
        assert(mDatabase.movieDao().getMovies().isNotEmpty())
    }

    @Test
    fun getMovie() {
        assert(mDatabase.movieDao().getMovie(movies[0].id!!).title == movies[0].title)
    }

    @Test
    @Throws(Exception::class)
    fun clearMovies() = runBlocking{
        mDatabase.movieDao().clearMovies()
        assert(mDatabase.movieDao().getMovies().isEmpty())
    }
}
