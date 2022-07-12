package com.example.android_popularmovies.presentation.movie.view_model

import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow


interface MovieDetailViewModel {
    val loadingState: StateFlow<Int>
    val detailState: StateFlow<MovieDetailState>
    val detailErrorState: SharedFlow<String>
    fun getMovieDetails(movieId: Int)
}