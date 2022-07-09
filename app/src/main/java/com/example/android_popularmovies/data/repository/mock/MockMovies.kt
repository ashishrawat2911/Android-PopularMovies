package com.example.android_popularmovies.data.repository.mock

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
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

        return MovieListApiModel(
            results = listOfMovies,
            page = RandomDataFactory.getRandomInt(),
            totalResults = RandomDataFactory.getRandomInt(),
            totalPages = RandomDataFactory.getRandomInt(),
        )
    }

    fun generateMovie(): MovieApiModel {
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

    fun generateMovieBelongingList(size: Int): MovieBelongingList {
        val listOfMovies = mutableListOf<MovieBelongingApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovieBelongings())
        }

        return MovieBelongingList(
            id = RandomDataFactory.getRandomLong(),
            page = RandomDataFactory.getRandomLong(),
            totalResults = RandomDataFactory.getRandomLong(),
            results = listOfMovies,
            totalPages = RandomDataFactory.getRandomLong(),
        )
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
