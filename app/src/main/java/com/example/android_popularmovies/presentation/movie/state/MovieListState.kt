package com.example.android_popularmovies.presentation.movie.state

sealed class MovieListState {
    object Loading : MovieListState()
    data class Error(var message: String) : MovieListState()
    data class Success(var movies: List<MovieStateData>) : MovieListState()
}

