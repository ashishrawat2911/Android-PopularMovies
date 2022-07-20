package com.example.android_popularmovies.presentation.movie.state

data class MovieStateData(
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Float,
    val overview: String,
)
