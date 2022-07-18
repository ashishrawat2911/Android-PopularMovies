package com.example.android_popularmovies.domain.mapper

import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDomainToStateMapper @Inject constructor() : Mapper<MovieDomainModel, MovieStateData> {
    override fun map(type: MovieDomainModel): MovieStateData {
        return MovieStateData(
            id = type.id,
            posterPath = type.posterPath,
            backdropPath = type.backdropPath,
            title = type.title,
            voteAverage = type.voteAverage,
            overview = type.overview
        )
    }
}

class MovieStateToDomainMapper @Inject constructor() : Mapper<MovieStateData, MovieDomainModel> {
    override fun map(type: MovieStateData): MovieDomainModel {
        return MovieDomainModel(
            id = type.id,
            posterPath = type.posterPath,
            backdropPath = type.backdropPath,
            title = type.title,
            voteAverage = type.voteAverage,
            overview = type.overview
        )
    }
}