package com.example.android_popularmovies.data.repository.mock

import com.example.android_popularmovies.data.source.remote.model.ISO639_1
import com.example.android_popularmovies.data.source.remote.model.ListType
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelonging
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
        val listOfMovies = mutableListOf<MovieBelonging>()
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

    private fun generateMovieBelongings(): MovieBelonging {
        return MovieBelonging(
            id = RandomDataFactory.getRandomLong(),
            posterPath = RandomDataFactory.getRandomImage(),
            description = RandomDataFactory.getRandomString(),
            favoriteCount = RandomDataFactory.getRandomLong(),
            iso639_1 = ISO639_1.En,
            itemCount = RandomDataFactory.getRandomLong(),
            listType = ListType.Movie,
            name = RandomDataFactory.getRandomString(),
        )
    }
}
