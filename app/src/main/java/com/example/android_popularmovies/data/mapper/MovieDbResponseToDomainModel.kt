package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDbResponseToDomainModel @Inject constructor() : Mapper<MovieDbModel, MovieDomainModel> {
    override fun map(type: MovieDbModel): MovieDomainModel {
        return with(type) {
            MovieDomainModel(
                id = id,
                title = title,
                posterPath = posterPath,
                overview = overview,
                voteAverage = voteAverage,
            )
        }
    }
}