package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDetailApiToDomainMapper @Inject constructor() :
    Mapper<MovieApiModel, MovieDetailDomainModel> {
    override fun map(type: MovieApiModel): MovieDetailDomainModel {
        return MovieDetailDomainModel(
            id = type.id,
            title = type.title,
            backdropPath = type.backdropPath,
            overview = type.overview,
            voteAverage = type.voteAverage,
        )
    }
}