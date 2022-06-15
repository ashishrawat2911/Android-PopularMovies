package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.di.qualifiers.MovieRepoQualifier
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieBelongingsUseCase @Inject constructor(
    @MovieRepoQualifier private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<List<MovieBelongingsEntity>> {
        return flow {
            val movieBelongingList = repository.getMovieBelongings(movieId)
            emit(movieBelongingList)
        }.map {
            it
        }
    }
}
