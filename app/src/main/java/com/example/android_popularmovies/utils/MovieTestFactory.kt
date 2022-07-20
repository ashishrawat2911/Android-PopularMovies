package com.example.android_popularmovies.utils

import com.example.android_popularmovies.data.mapper.MovieResponseToDomainModel
import com.example.android_popularmovies.data.mapper.MovieDetailResponseToDomainModel
import com.example.android_popularmovies.data.model.MovieListResponseModel
import com.example.android_popularmovies.data.model.MovieResponseModel
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel

object MovieTestFactory {
    private fun generateListOfMovies(size: Int): List<MovieResponseModel> {
        val listOfMovies = mutableListOf<MovieResponseModel>()
        repeat(size) {
            listOfMovies.add(generateMovieResponseModel())
        }
        return listOfMovies
    }

    fun generateMovieListModel(size: Int): MovieListResponseModel {
        val listOfMovies = mutableListOf<MovieResponseModel>()
        repeat(size) {
            listOfMovies.add(generateMovieResponseModel())
        }

        return MovieListResponseModel(
            results = listOfMovies,
            page = RandomDataFactory.getRandomInt(),
            totalResults = RandomDataFactory.getRandomInt(),
            totalPages = RandomDataFactory.getRandomInt(),
        )
    }

    fun generateMovieResponseModel(): MovieResponseModel {
        return MovieResponseModel(
            id = RandomDataFactory.getRandomInt(),
            title = RandomDataFactory.getRandomString(),
            voteAverage = RandomDataFactory.getRandomFloat(),
            posterPath = RandomDataFactory.getRandomImage(),
            backdropPath = RandomDataFactory.getRandomImage(),
            overview = RandomDataFactory.getRandomString(),
        )
    }

    fun generateMovieDetailEntity(): MovieDetailDomainModel {
        return MovieDetailResponseToDomainModel().map(generateMovieResponseModel())
    }

    fun generateListOfMovieEntity(size: Int): List<MovieDomainModel> {
        return generateListOfMovies(size).map { MovieResponseToDomainModel().map(it) }
    }
}
