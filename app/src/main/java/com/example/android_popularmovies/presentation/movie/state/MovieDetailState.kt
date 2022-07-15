package com.example.android_popularmovies.presentation.movie.state


sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Error(var message: String) : MovieDetailState()
    data class Success(var movie: MovieStateData) : MovieDetailState()
}