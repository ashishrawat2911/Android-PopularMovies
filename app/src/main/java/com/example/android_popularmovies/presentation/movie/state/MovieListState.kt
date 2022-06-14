package com.example.android_popularmovies.presentation.movie.state

import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.utils.ResultState

data class MovieListState(
    var movieResultState: ResultState<List<MovieStateData>>
)
data class MovieDetailState(
    var movieResultState: ResultState<MovieStateData>
)

data class MovieStateData(
    var id: Int,
    var posterPath: String,
    var backdropPath: String,
    var title: String,
    var voteAverage: Float,
    var overview: String,
)