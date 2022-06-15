package com.example.android_popularmovies.data.repository.mock

import com.example.android_popularmovies.data.source.remote.model.*
import com.example.android_popularmovies.utils.RandomDataFactory

object MockMovies {

    fun generateListOfMovies(size: Int): List<MovieApiModel> {
        val listOfMovies = mutableListOf<MovieApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovie())
        }
        return listOfMovies
    }

    fun generateMovieListModel(size: Int): MovieListApiModel {
        val listOfMovies = mutableListOf<MovieApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovie())
        }

        val model = MovieListApiModel()
        model.results = listOfMovies
        model.page = RandomDataFactory.getRandomInt()
        model.totalResults = RandomDataFactory.getRandomInt()
        return model
    }

    fun generateMovie(): MovieApiModel {
        return MovieApiModel(
            adult = RandomDataFactory.getRandomBoolean(),
            id = RandomDataFactory.getRandomInt(),
            title = RandomDataFactory.getRandomString(),
            voteAverage = RandomDataFactory.getRandomFloat(),
            posterPath = RandomDataFactory.getRandomImage(),
            popularity = RandomDataFactory.getRandomFloat(),
            backdropPath = RandomDataFactory.getRandomImage(),
            originalLanguage = RandomDataFactory.getRandomString(),
            originalTitle = RandomDataFactory.getRandomString(),
            overview = RandomDataFactory.getRandomString(),
            releaseDate = RandomDataFactory.getRandomString(),
            video = RandomDataFactory.getRandomBoolean(),
            voteCount = RandomDataFactory.getRandomInt()
        )
    }

    fun generateMovieBelongingList(size: Int): MovieBelongingList {
        val listOfMovies = mutableListOf<MovieBelongingApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovieBelongings())
        }

        val model = MovieBelongingList(
            id = RandomDataFactory.getRandomLong(),
            page = RandomDataFactory.getRandomLong(),
            totalResults = RandomDataFactory.getRandomLong(),
            results = listOfMovies,
            totalPages = RandomDataFactory.getRandomLong(),
        )
        return model
    }

    private fun generateMovieBelongings(): MovieBelongingApiModel {
        return MovieBelongingApiModel(
            id = RandomDataFactory.getRandomLong(),
            posterPath = RandomDataFactory.getRandomImage(),
            description = RandomDataFactory.getRandomString(),
            favoriteCount = RandomDataFactory.getRandomLong(),
            itemCount = RandomDataFactory.getRandomLong(),
            name = RandomDataFactory.getRandomString(),
        )
    }
}
