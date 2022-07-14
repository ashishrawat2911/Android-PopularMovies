package com.example.android_popularmovies.presentation.movie.view_model

import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MovieListViewModel {
    val loadingState: StateFlow<Int>
    val filterState: SharedFlow<List<MovieStateData>>
    val movieState: StateFlow<MovieListState>
    fun fetchMoviesList()
    fun filterMovies(text: String)
}
