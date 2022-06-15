package com.example.android_popularmovies.presentation.movie.state

import com.example.android_popularmovies.utils.ResultState

data class MovieListState(
    var movieResultState: ResultState<List<MovieStateData>>
)
data class MovieDetailState(
    var movieResultState: ResultState<MovieStateData>
)

data class MovieBelongingState(
    var movieResultState: ResultState<List<MovieBelongingData>>
)

data class MovieStateData(
    var id: Int,
    var posterPath: String,
    var backdropPath: String,
    var title: String,
    var voteAverage: Float,
    var overview: String,
)
data class MovieBelongingData(
    val description: String,
    val favoriteCount: Long,
    val id: Long,
    val itemCount: Long,
    val name: String,
    val posterPath: Any? = null
)