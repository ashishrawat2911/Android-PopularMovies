package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.di.qualifiers.MockMovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MockGetMoviesUseCase @Inject constructor(
    @MockMovieRepoQualifier private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return repository.getMovies()
    }
}
