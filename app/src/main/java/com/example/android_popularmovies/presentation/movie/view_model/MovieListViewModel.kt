package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.mapper.MovieDomainToStateMapper
import com.example.android_popularmovies.domain.mapper.MovieStateToDomainMapper
import com.example.android_popularmovies.domain.usecase.FilterMoviesUseCase
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.utils.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val filterMoviesUseCase: FilterMoviesUseCase,
    private val appDispatchers: AppDispatchers,
    private val movieDomainToStateMapper: MovieDomainToStateMapper,
    private val movieStateToDomainMapper: MovieStateToDomainMapper
) : ViewModel() {
    val uiState: StateFlow<MovieListState>
        get() = _uiState
    private val _uiState = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val errorState: SharedFlow<String>
        get() = _errorState
    private val _errorState = MutableSharedFlow<String>()
    val onTapDetailState: SharedFlow<Int>
        get() = _onTapDetailState
    private val _onTapDetailState = MutableSharedFlow<Int>()

    fun fetchMoviesList() {
        viewModelScope.launch(appDispatchers.IO) {
            getMoviesUseCase().collectLatest { it ->
                when (it) {
                    is NetworkResult.Success -> {
                        _uiState.value =
                            MovieListState.Success(it.data.map { movieDomainToStateMapper.map(it) })
                    }
                    is NetworkResult.Error -> {
                        Timber.e(it.error)
                        _errorState.emit(it.error)
                    }
                }
            }
        }
    }

    fun filterMovies(text: String): List<MovieStateData> {
        val state = _uiState.value
        return if (state is MovieListState.Success) {
            filterMoviesUseCase(
                state.movies.map { movieStateToDomainMapper.map(it) },
                text
            ).map { movieDomainToStateMapper.map(it) }
        } else {
            mutableListOf()
        }
    }

    fun onDetailTap(id: Int) {
        viewModelScope.launch {
            _onTapDetailState.emit(id)
        }
    }
}