package com.example.android_popularmovies.domain.usecase.impl

import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.domain.usecase.FilterMoviesUseCase
import javax.inject.Inject

class FilterMoviesUseCaseImpl @Inject constructor() : FilterMoviesUseCase {
    override operator fun invoke(
        movies: List<MovieDomainModel>,
        text: String
    ) = movies.filter {
        it.title.lowercase().contains(text.lowercase())
    }
}
