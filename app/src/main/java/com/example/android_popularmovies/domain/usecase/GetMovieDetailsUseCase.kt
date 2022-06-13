package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.di.qualifiers.MovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    @MovieRepoQualifier private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieEntity {
        return repository.getMovieDetails(movieId)
    }
}