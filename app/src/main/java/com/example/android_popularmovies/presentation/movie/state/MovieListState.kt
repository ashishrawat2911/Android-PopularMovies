package com.example.android_popularmovies.presentation.movie.state


sealed class MovieListState {
    object Loading : MovieListState()
    data class Success(val movies: List<MovieStateData>) : MovieListState()
    data class Error(val error: String) : MovieListState()
}
