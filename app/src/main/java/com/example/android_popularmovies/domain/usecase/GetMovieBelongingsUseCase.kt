package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.source.remote.model.MovieBelonging
import com.example.android_popularmovies.di.qualifiers.MovieRepoQualifier
import com.example.android_popularmovies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieBelongingsUseCase @Inject constructor(
    @MovieRepoQualifier private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Flow<List<MovieBelonging>?> {
        return flow {
            val movieBelongingList = repository.getMovieBelongings(movieId)
            emit(movieBelongingList)
        }.flowOn(Dispatchers.IO).map {
            it.body()?.results
        }
    }
}