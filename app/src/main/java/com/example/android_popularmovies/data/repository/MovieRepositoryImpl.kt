package com.example.android_popularmovies.data.repository

import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.data.source.remote.MovieDataService
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import com.example.android_popularmovies.domain.repository.MovieRepository
import com.example.android_popularmovies.utils.getMovieErrorMessage
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private var movieDataService: MovieDataService
) : MovieRepository {

    override suspend fun getMovies(): NetworkResult<List<MovieDomainModel>> {
        return try {
            return NetworkResult.Success(movieDataService.getMovies())
        } catch (e: Exception) {
            NetworkResult.Error(e.getMovieErrorMessage())
        }
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkResult<MovieDetailDomainModel> {
        return try {
            NetworkResult.Success(movieDataService.getMovieDetails(movieId))
        } catch (e: Exception) {
            NetworkResult.Error(e.getMovieErrorMessage())
        }
    }
}
