package com.example.android_popularmovies.data.source.remote.impl

import com.example.android_popularmovies.data.mapper.MovieApiToDomainMapper
import com.example.android_popularmovies.data.source.remote.MovieApiService
import com.example.android_popularmovies.data.source.remote.MovieDataService
import com.example.android_popularmovies.domain.model.MovieDomainModel
import javax.inject.Inject

class MovieDataServiceImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieApiToDomainMapper: MovieApiToDomainMapper,
) : MovieDataService {
    override suspend fun getMovies(): List<MovieDomainModel> {
        return movieApiService.popularMovies().results.map {
            movieApiToDomainMapper.map(it)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDomainModel {
        return movieApiToDomainMapper.map(movieApiService.movieDetails(movieId))
    }
}