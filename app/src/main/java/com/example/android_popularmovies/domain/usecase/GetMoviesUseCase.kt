package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.di.qualifiers.MovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    @MovieRepoQualifier private val repository: MovieRepository
) {
    operator fun invoke(): Single<List<MovieEntity>> {
        return repository.getMovies()
    }
}
