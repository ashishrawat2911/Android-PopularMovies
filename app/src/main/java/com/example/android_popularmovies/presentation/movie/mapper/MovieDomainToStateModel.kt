package com.example.android_popularmovies.presentation.movie.mapper

import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.utils.Constants.Companion.movieImagePath
import com.example.android_popularmovies.utils.Mapper
import javax.inject.Inject

class MovieDomainToStateModel @Inject constructor() : Mapper<MovieDomainModel, MovieStateData> {
    override fun map(type: MovieDomainModel): MovieStateData {
        with(type) {
            return MovieStateData(
                id = id,
                posterPath = movieImagePath + posterPath,
                title = title,
                voteAverage = voteAverage,
                overview = overview
            )
        }
    }
}
