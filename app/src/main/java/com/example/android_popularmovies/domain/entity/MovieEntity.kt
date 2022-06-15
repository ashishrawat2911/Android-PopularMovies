package com.example.android_popularmovies.domain.entity

data class MovieEntity(
    var id: Int,
    var posterPath: String,
    var backdropPath: String,
    var title: String,
    var voteAverage: Float,
    var overview: String,
)
