package com.example.android_popularmovies.domain.usecase

import com.example.android_popularmovies.data.source.remote.model.Movie
import com.example.android_popularmovies.data.source.remote.model.MovieListModel
import com.example.android_popularmovies.di.qualifiers.MovieRepoQualifier
import com.example.android_popularmovies.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    @MovieRepoQualifier private val repository: MovieRepository
) {

    fun getPopularMovies(): Single<MovieListModel> {
        return repository.loadMovies()
    }


   suspend fun cacheMovies(movies: List<Movie>) {
       repository.cacheMovie(movies);
   }

    suspend fun getCacheMovies(): List<Movie> {
        return repository.getCacheMovies();
    }
}