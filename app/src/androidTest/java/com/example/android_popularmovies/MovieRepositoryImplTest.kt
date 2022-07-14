package com.example.android_popularmovies

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.utils.MockMovies
import com.example.android_popularmovies.data.source.local.MovieDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    private lateinit var movieDatabase: MovieDatabase

    @Before
    fun setUp() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun addMovieToDB() = runBlocking {
        val movies = MockMovies.generateListOfMovies(10)
        movieDatabase.movieDao().addMovies(
            movies.map {
                it.toEntity().toDBModel()
            }
        )
        assert(movieDatabase.movieDao().getMovies().isNotEmpty())
    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }
}
