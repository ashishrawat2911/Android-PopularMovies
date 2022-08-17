package com.example.android_popularmovies.presentation.movie.mapper

import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject
class MovieStateToDomainModel @Inject constructor() : Mapper<MovieStateData, MovieDomainModel> {
    override fun map(type: MovieStateData): MovieDomainModel {
        with(type) {
            return MovieDomainModel(
                id = id,
                posterPath = posterPath,
                title = title,
                voteAverage = voteAverage,
                overview = overview
            )
        }
    }
}
