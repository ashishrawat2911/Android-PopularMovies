package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieApiToDomainMapper @Inject constructor() : Mapper<MovieApiModel, MovieDomainModel> {
    override fun map(type: MovieApiModel): MovieDomainModel {
        return MovieDomainModel(
            id = type.id,
            title = type.title,
            backdropPath = type.backdropPath,
            posterPath = type.posterPath,
            overview = type.overview,
            voteAverage = type.voteAverage,
        )
    }
}

