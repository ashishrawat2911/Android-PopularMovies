package com.example.android_popularmovies.data.repository.mock

import com.example.android_popularmovies.data.mapper.MovieApiToEntityMapper
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.utils.RandomDataFactory

object MockMovies {

    fun generateListOfMovies(size: Int): List<MovieApiModel> {
        val listOfMovies = mutableListOf<MovieApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovieApiModel())
        }
        return listOfMovies
    }

    fun generateMovieListModel(size: Int): MovieListApiModel {
        val listOfMovies = mutableListOf<MovieApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovieApiModel())
        }

        return MovieListApiModel(
            results = listOfMovies,
            page = RandomDataFactory.getRandomInt(),
            totalResults = RandomDataFactory.getRandomInt(),
            totalPages = RandomDataFactory.getRandomInt(),
        )
    }

    fun generateMovieApiModel(): MovieApiModel {
        return MovieApiModel(
            id = RandomDataFactory.getRandomInt(),
            title = RandomDataFactory.getRandomString(),
            voteAverage = RandomDataFactory.getRandomFloat(),
            posterPath = RandomDataFactory.getRandomImage(),
            backdropPath = RandomDataFactory.getRandomImage(),
            originalLanguage = RandomDataFactory.getRandomString(),
            overview = RandomDataFactory.getRandomString(),
        )
    }

    fun generateMovieEntity(): MovieEntity {
        return MovieApiToEntityMapper().map(generateMovieApiModel())
    }

    fun generateListOfMovieEntity(size: Int): List<MovieEntity> {
        return generateListOfMovies(size).map { MovieApiToEntityMapper().map(it) }


    }
}
