package com.example.android_popularmovies.domain.mapper

import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.presentation.movie.state.MovieStateData

fun MovieEntity.toState(): MovieStateData {
    return MovieStateData(
        id = id,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = title,
        voteAverage = voteAverage,
        overview = overview
    )
}