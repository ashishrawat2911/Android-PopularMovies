package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.model.MovieResponseModel
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDetailResponseToDomainModel @Inject constructor() :
    Mapper<MovieResponseModel, MovieDetailDomainModel> {
    override fun map(type: MovieResponseModel): MovieDetailDomainModel {
        return MovieDetailDomainModel(
            detailTitle = type.title,
            detailBackdropPath = type.backdropPath,
            detailOverview = type.overview,
            detailVoteAverage = type.voteAverage,
        )
    }
}
