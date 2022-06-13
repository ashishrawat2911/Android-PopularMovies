package com.example.android_popularmovies.domain.entity

data class MovieListEntity(
    var page: Int? = null,
    var totalResults: Int? = null,
    var totalPages: Int? = null,
    var results: List<MovieEntity>? = null,
)

data class MovieEntity(
    var id: Int,
    var posterPath: String,
    var backdropPath: String,
    var title: String,
    var voteAverage: Float,
    var overview: String,
)
