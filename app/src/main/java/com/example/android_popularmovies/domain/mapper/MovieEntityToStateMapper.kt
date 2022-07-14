package com.example.android_popularmovies.domain.mapper

import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieEntityToStateMapper @Inject constructor() : Mapper<MovieEntity, MovieStateData> {
    override fun map(type: MovieEntity): MovieStateData {
        return MovieStateData(
            id = type.id,
            posterPath = type.posterPath,
            backdropPath = type.backdropPath,
            title = type.title,
            voteAverage = type.voteAverage,
            overview = type.overview
        )
    }
}