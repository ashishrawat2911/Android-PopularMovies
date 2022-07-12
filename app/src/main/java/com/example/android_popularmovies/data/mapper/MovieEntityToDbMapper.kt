package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.utils.Mapper

class MovieEntityToDbMapper : Mapper<MovieEntity, MovieDbModel> {
    override fun map(type: MovieEntity): MovieDbModel {
        return MovieDbModel(
            id = type.id,
            title = type.title,
            backdropPath = type.backdropPath,
            posterPath = type.posterPath,
            overview = type.overview,
            voteAverage = type.voteAverage,
        )
    }
}