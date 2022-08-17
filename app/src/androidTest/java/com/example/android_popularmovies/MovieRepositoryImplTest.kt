package com.example.android_popularmovies

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android_popularmovies.data.source.local.MovieDatabase
import com.example.android_popularmovies.utils.MockMovies
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    private lateinit var movieDatabase: MovieDatabase
    private val movieApiToEntityMapper: MovieApiToEntityMapper = MovieApiToEntityMapper()
    private val movieEntityToDbMapper: MovieEntityToDbMapper = MovieEntityToDbMapper()

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
                movieEntityToDbMapper.map(movieApiToEntityMapper.map(it))
            }
        )
        assert(movieDatabase.movieDao().getMovies().isNotEmpty())
    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }
}
