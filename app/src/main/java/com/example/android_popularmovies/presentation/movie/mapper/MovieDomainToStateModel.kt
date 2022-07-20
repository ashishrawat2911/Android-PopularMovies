package com.example.android_popularmovies.presentation.movie.mapper

import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDomainToStateModel @Inject constructor() : Mapper<MovieDomainModel, MovieStateData> {
    override fun map(type: MovieDomainModel): MovieStateData {
        return MovieStateData(
            id = type.id,
            posterPath = type.posterPath,
            title = type.title,
            voteAverage = type.voteAverage,
            overview = type.overview
        )
    }
}
