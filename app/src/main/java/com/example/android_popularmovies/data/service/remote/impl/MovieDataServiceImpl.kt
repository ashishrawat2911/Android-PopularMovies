package com.example.android_popularmovies.data.source.remote.impl

import com.example.android_popularmovies.data.mapper.MovieDetailResponseToDomainModel
import com.example.android_popularmovies.data.mapper.MovieResponseToDomainModel
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.MovieDataService
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import javax.inject.Inject

class MovieDataServiceImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieResponseToDomainModel: MovieResponseToDomainModel,
    private val movieDetailResponseToDomainModel: MovieDetailResponseToDomainModel,
) : MovieDataService {
    override suspend fun getMovies(): List<MovieDomainModel> {
        return movieApiService.popularMovies().results.map {
            movieResponseToDomainModel.map(it)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailDomainModel {
        return movieDetailResponseToDomainModel.map(movieApiService.movieDetails(movieId))
    }
}
