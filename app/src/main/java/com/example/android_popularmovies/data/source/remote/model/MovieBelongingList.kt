package com.example.android_popularmovies.data.source.remote.model

data class MovieBelongingList(
    val id: Long,
    val page: Long,
    val results: List<MovieBelongingApiModel>,
    val totalPages: Long,
    val totalResults: Long
)

data class MovieBelongingApiModel(
    val description: String,
    val favoriteCount: Long,
    val id: Long,
    val itemCount: Long,
    val name: String,
    val posterPath: Any? = null
)
