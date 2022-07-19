package com.example.android_popularmovies.domain.model

data class MovieDetailDomainModel(
    val id: Int,
    val backdropPath: String,
    val title: String,
    val voteAverage: Float,
    val overview: String,
)