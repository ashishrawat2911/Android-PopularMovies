package com.example.android_popularmovies.domain.model

data class MovieDomainModel(
    val id: Int,
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val voteAverage: Float,
    val overview: String,
)
