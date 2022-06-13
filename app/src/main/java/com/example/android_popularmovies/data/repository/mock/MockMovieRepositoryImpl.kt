package com.example.android_popularmovies.data.repository.mock

import com.example.android_popularmovies.data.mapper.toEntity
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingList
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import retrofit2.Response

class MockMovieRepositoryImpl(
) : MovieRepository {
    override fun getMovies(): Single<List<MovieEntity>> {
        return Single.just(MockMovies.generateMovieListModel(10).results!!.map { it.toEntity() })
    }

    override suspend fun getMovieDetails(movieId: Int): MovieEntity {
        return MockMovies.generateMovie().toEntity()
    }

    override suspend fun getMovieBelongings(movieId: Int): Response<MovieBelongingList> {
        return Response.success(MockMovies.generateMovieBelongingList(10))
    }
}