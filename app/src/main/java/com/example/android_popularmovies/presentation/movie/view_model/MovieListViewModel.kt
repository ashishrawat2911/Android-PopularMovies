package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.AppDispatchers
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.utils.ResultState
import com.example.android_popularmovies.utils.getMovieErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val appDispatchers: AppDispatchers
) : ViewModel() {
    val movieState: LiveData<MovieListState> get() = _movieState
    private val _movieState = MutableLiveData<MovieListState>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _movieState.postValue(MovieListState(movieResultState = ResultState.Error(exception.getMovieErrorMessage())))
    }

    init {
        _movieState.postValue(
            MovieListState(
                movieResultState = ResultState.Loading()
            )
        )
        fetchMoviesList()
    }

    fun fetchMoviesList() = viewModelScope.launch(appDispatchers.IO + exceptionHandler) {
        val movies = async { getMoviesUseCase() }
        _movieState.postValue(
            MovieListState(
                movieResultState = ResultState.Success(
                    movies.await().map { it.toState() })
            )
        )

    }
    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
