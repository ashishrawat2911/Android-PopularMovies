package com.example.android_popularmovies.data.store.impl

import com.example.android_popularmovies.data.mapper.MovieDbResponseToDomainDetailsModel
import com.example.android_popularmovies.data.mapper.MovieDbResponseToDomainModel
import com.example.android_popularmovies.data.mapper.MovieDetailResponseToDomainModel
import com.example.android_popularmovies.data.mapper.MovieResponseToDbModel
import com.example.android_popularmovies.data.mapper.MovieResponseToDomainModel
import com.example.android_popularmovies.data.source.MovieLocalDataSource
import com.example.android_popularmovies.data.source.MovieRemoteDataSource
import com.example.android_popularmovies.data.store.MovieDataStore
import com.example.android_popularmovies.domain.model.MovieDetailDomainModel
import com.example.android_popularmovies.domain.model.MovieDomainModel
import javax.inject.Inject

class MovieDataStoreImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieResponseToDomainModel: MovieResponseToDomainModel,
    private val movieDetailResponseToDomainModel: MovieDetailResponseToDomainModel,
    private val movieDbResponseToDomainModel: MovieDbResponseToDomainModel,
    private val movieDbResponseToDomainDetailsModel: MovieDbResponseToDomainDetailsModel,
    private val movieResponseToDbModel: MovieResponseToDbModel,
) : MovieDataStore {
    override suspend fun getMovies(): List<MovieDomainModel> {
        val cacheMovies = movieLocalDataSource.getMovies();
        return if (cacheMovies.isEmpty()) {
            val movies = movieRemoteDataSource.getMovies()
            movieLocalDataSource.setMoviesToCache(movies.map { movieResponseToDbModel.map(it) });
            movies.map { movieResponse ->
                movieResponseToDomainModel.map(movieResponse)
            }
        } else {
            cacheMovies.map { movieDbResponseToDomainModel.map(it) }
        }

    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailDomainModel {
        val cacheMovie = movieLocalDataSource.getMovie(movieId);
        return if (cacheMovie == null) {
            movieDetailResponseToDomainModel.map(
                movieRemoteDataSource.getMovieDetails(
                    movieId
                )
            )
        } else {
            movieDbResponseToDomainDetailsModel.map(cacheMovie)
        }
    }
}
