package com.example.android_popularmovies.presentation.movie.view_model.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.mapper.MovieEntityToStateMapper
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.presentation.movie.view_model.MovieListViewModel
import com.example.android_popularmovies.utils.AppDispatchers
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
    override val movieState: StateFlow<MovieListState> get() = _movieState
    private val _movieState = MutableStateFlow<MovieListState>(
        MovieListState.Loading
    )
    override val filterState: SharedFlow<List<MovieStateData>> get() = _filterState
    private val _filterState = MutableSharedFlow<List<MovieStateData>>()

    override fun fetchMoviesList() {
        viewModelScope.launch(appDispatchers.IO) {
            val movies = getMoviesUseCase()
            movies.catch { exception ->
                Timber.e(exception)
                _movieState.value = MovieListState.Error(exception.getMovieErrorMessage())
            }.collectLatest {
                _movieState.value =
                    MovieListState.Success(it.map { movieEntityToStateMapper.map(it) })
            }
        }
    }

    override fun filterMovies(text: String) {
        viewModelScope.launch {
            val moviesResult = movieState.value
            if (moviesResult is MovieListState.Success) {
                val temp: MutableList<MovieStateData> = ArrayList()
                for (d in moviesResult.movies) {
                    if (d.title.lowercase().contains(text.lowercase())) {
                        temp.add(d)
                    }
                }
                _filterState.emit(temp)
            }
        }
    }

    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}