package com.example.android_popularmovies.presentation.movie.state

import com.example.android_popularmovies.utils.ResultState

data class MovieDetailState(
    var movieResultState: ResultState<MovieStateData>
)