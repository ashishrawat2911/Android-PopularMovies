package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.domain.model.MovieDomainModel

interface FilterMoviesUseCase {
    operator fun invoke(movies: List<MovieDomainModel>, text: String): List<MovieDomainModel>
}
