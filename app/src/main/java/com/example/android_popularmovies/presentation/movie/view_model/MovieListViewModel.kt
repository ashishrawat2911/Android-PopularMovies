package com.example.android_popularmovies.presentation.movie.view_model

import com.example.android_popularmovies.presentation.movie.state.MovieListState
import kotlinx.coroutines.flow.StateFlow

interface MovieListViewModel {
    val loadingState: StateFlow<Int>
    val movieState: StateFlow<MovieListState>
    fun fetchMoviesList()
    fun filterMovies(text: String)
}
