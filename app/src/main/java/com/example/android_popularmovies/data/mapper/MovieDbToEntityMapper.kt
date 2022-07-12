package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.utils.Mapper

class MovieDbToEntityMapper : Mapper<MovieDbModel, MovieEntity> {
    override fun map(type: MovieDbModel): MovieEntity {
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