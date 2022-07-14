package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.utils.Mapper

class MovieApiToEntityMapper : Mapper<MovieApiModel, MovieEntity> {
    override fun map(type: MovieApiModel): MovieEntity {
        return MovieEntity(
            id = type.id,
            title = type.title,
            backdropPath = type.backdropPath,
            posterPath = type.posterPath,
            overview = type.overview,
            voteAverage = type.voteAverage,
        )
    }
}

