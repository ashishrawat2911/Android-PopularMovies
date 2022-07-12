package com.example.android_popularmovies.presentation.movie.view_model.impl

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.mapper.MovieEntityToStateMapper
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.view_model.MovieListViewModel
import com.example.android_popularmovies.utils.AppDispatchers
import com.example.android_popularmovies.utils.ResultState
import com.example.android_popularmovies.utils.getMovieErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModelImpl @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val appDispatchers: AppDispatchers,
    private val movieEntityToStateMapper: MovieEntityToStateMapper
) : ViewModel(), MovieListViewModel {
    override val loadingState: StateFlow<Int>
        get() = _loadingState
    private val _loadingState = MutableStateFlow(
        View.GONE
    )
    override val movieState: StateFlow<MovieListState> get() = _movieState
    private val _movieState = MutableStateFlow(
        MovieListState(
            movieResultState = ResultState.Loading()
        )
    )

    init {
        fetchMoviesList()
    }

    override fun fetchMoviesList() {
        viewModelScope.launch(appDispatchers.IO) {
            val movies = getMoviesUseCase()
            movies.onStart {
                _loadingState.value = View.VISIBLE
            }.onCompletion {
                _loadingState.value = View.GONE
            }.catch { exception ->
                Timber.e(exception)
                _movieState.value =
                    MovieListState(movieResultState = ResultState.Error(exception.getMovieErrorMessage()))
            }.collectLatest {
                _movieState.value =
                    MovieListState(
                        movieResultState = ResultState.Success(
                            it.map { movieEntityToStateMapper.map(it) })
                    )
            }
        }
    }

    override fun filterMovies(text: String) {

    }

    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}