package com.example.android_popularmovies.presentation.movie.state

import com.example.android_popularmovies.data.source.remote.model.MovieApiModel

sealed class MovieState {
    object Init : MovieState()
    object Loading : MovieState()
    data class Error(var message: String) : MovieState()
    data class MovieListSuccess(var listOfMovieViews: List<MovieApiModel>?) : MovieState()
}
