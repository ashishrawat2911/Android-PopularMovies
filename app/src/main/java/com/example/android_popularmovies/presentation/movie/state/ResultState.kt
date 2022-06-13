package com.example.android_popularmovies.presentation.movie.state

sealed class ResultState<T> {
    class Init<T> : ResultState<T>()
    class Loading<T> : ResultState<T>()
    data class Error<T>(var message: String) : ResultState<T>()
    data class Success<T>(var result: T) : ResultState<T>()
}
