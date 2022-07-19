package com.example.android_popularmovies.data.source.remote.impl

import com.example.android_popularmovies.data.mapper.MovieApiToDomainModel
import com.example.android_popularmovies.data.mapper.MovieDetailApiToDomainModel
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.MovieDataService
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import javax.inject.Inject

class MovieDataServiceImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieApiToDomainModel: MovieApiToDomainModel,
    private val movieDetailApiToDomainModel: MovieDetailApiToDomainModel,
) : MovieDataService {
    override suspend fun getMovies(): List<MovieDomainModel> {
        return movieApiService.popularMovies().results.map {
            movieApiToDomainModel.map(it)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailDomainModel {
        return movieDetailApiToDomainModel.map(movieApiService.movieDetails(movieId))
    }
}