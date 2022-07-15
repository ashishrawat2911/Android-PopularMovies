package com.example.android_popularmovies.presentation.movie.state

data class MovieStateData(
    var id: Int,
    var posterPath: String,
    var backdropPath: String,
    var title: String,
    var voteAverage: Float,
    var overview: String,
)