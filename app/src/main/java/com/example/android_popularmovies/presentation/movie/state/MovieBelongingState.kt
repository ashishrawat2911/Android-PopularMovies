package com.example.android_popularmovies.presentation.movie.state

import com.example.android_popularmovies.utils.ResultState

data class MovieBelongingState(
    var movieResultState: ResultState<List<MovieBelongingData>>
)

data class MovieBelongingData(
    val description: String,
    val favoriteCount: Long,
    val id: Long,
    val itemCount: Long,
    val name: String,
    val posterPath: Any? = null
)
