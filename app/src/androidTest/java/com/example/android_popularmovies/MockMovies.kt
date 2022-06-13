package com.example.android_popularmovies

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel

object MockMovies {

    fun generateListOfMovies(size: Int): List<MovieApiModel> {
        val listOfMovies = mutableListOf<MovieApiModel>()
        repeat(size) {
            listOfMovies.add(generateMovie())
        }
        return listOfMovies
    }

    private fun generateMovie(): MovieApiModel {
        return MovieApiModel(
            adult = RandomDataFactory.getRandomBoolean(),
            id = RandomDataFactory.getRandomInt(),
            title = RandomDataFactory.getRandomString(),
            voteAverage = RandomDataFactory.getRandomFloat(),
            posterPath = RandomDataFactory.getRandomString(),
            popularity = RandomDataFactory.getRandomFloat(),
            backdropPath = RandomDataFactory.getRandomString(),
            originalLanguage = RandomDataFactory.getRandomString(),
            originalTitle = RandomDataFactory.getRandomString(),
            overview = RandomDataFactory.getRandomString(),
            releaseDate = RandomDataFactory.getRandomString(),
            video = RandomDataFactory.getRandomBoolean(),
            voteCount = RandomDataFactory.getRandomInt()
        )
    }
}
