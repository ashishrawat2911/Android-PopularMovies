package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.utils.NetworkResult
import com.example.android_popularmovies.utils.getMovieErrorMessage
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDataStore: MovieDataStore
) : MovieRepository {

    override suspend fun getMovies(): NetworkResult<List<MovieDomainModel>> {
        return try {
            return NetworkResult.Success(movieDataStore.getMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.getMovieErrorMessage())
        }
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkResult<MovieDetailDomainModel> {
        return try {
            NetworkResult.Success(movieDataStore.getMovieDetails(movieId))
        } catch (e: Exception) {
            NetworkResult.Error(e.getMovieErrorMessage())
        }
    }
}
