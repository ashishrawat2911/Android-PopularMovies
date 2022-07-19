package com.example.android_popularmovies.presentation.movie.state

data class MovieDetailStateData(
    val id: Int,
    val backdropPath: String,
    val title: String,
    val voteAverage: Float,
    val overview: String,
)