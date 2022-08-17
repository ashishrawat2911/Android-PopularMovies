package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDbResponseToDomainDetailsModel @Inject constructor() :
    Mapper<MovieDbModel, MovieDetailDomainModel> {
    override fun map(type: MovieDbModel): MovieDetailDomainModel {
        return with(type) {
            MovieDetailDomainModel(
                detailBackdropPath = backdropPath,
                detailTitle = title,
                detailVoteAverage = voteAverage,
                detailOverview = overview
            )
        }
    }
}