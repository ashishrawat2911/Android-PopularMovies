package com.example.android_popularmovies.presentation.movie.mapper

import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.presentation.movie.state.MovieDetailStateData
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDetailDomainToStateModel @Inject constructor() :
    Mapper<MovieDetailDomainModel, MovieDetailStateData> {
    override fun map(type: MovieDetailDomainModel): MovieDetailStateData {
        return MovieDetailStateData(
            id = type.id,
            title = type.title,
            voteAverage = type.voteAverage,
            overview = type.overview,
            backdropPath = type.backdropPath,
        )
    }
}