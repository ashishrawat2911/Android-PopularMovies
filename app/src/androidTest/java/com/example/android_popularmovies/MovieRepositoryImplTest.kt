package com.example.android_popularmovies

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android_popularmovies.data.mapper.toDBModel
import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.repository.mock.MockMovies
import com.example.android_popularmovies.data.source.local.MovieDatabase
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
    fun addMovieToDB() {
        val movies = MockMovies.generateListOfMovies(10)
        movieDatabase.movieDao().addMovies(
            movies.map {
                it.toEntity().toDBModel()
            }
        )
        movieDatabase.movieDao().getMovies().test().assertValue {
            it.isNotEmpty()
        }
    }
    @After
    fun tearDown() {
        movieDatabase.close()
    }
}
