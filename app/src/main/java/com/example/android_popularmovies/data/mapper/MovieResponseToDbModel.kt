package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.model.MovieResponseModel
import com.example.android_popularmovies.data.source.local.database.model.MovieDbModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieResponseToDbModel @Inject constructor() :
    Mapper<MovieResponseModel, MovieDbModel> {
    override fun map(type: MovieResponseModel): MovieDbModel {
        return with(type) {
            MovieDbModel(
                id = id,
                title = title,
                posterPath = posterPath,
                overview = overview,
                voteAverage = voteAverage,
                backdropPath = backdropPath,
            )
        }
    }
}