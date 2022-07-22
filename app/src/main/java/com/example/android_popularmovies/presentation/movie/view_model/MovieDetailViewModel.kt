package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.data.NetworkResult
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.mapper.MovieDetailDomainToStateModel
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.utils.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesUseCase: GetMovieDetailsUseCase,
    private val appDispatchers: AppDispatchers,
    private val movieDetailDomainToStateModel: MovieDetailDomainToStateModel
) : ViewModel() {
    val uiState: StateFlow<MovieDetailState>
        get() = _uiState
    private val _uiState = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val detailErrorState: SharedFlow<String>
        get() = _detailErrorState
    private val _detailErrorState = MutableSharedFlow<String>()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            getMoviesUseCase(movieId).collectLatest { movieResult ->
                when (movieResult) {
                    is NetworkResult.Success -> {
                        _uiState.value =
                            MovieDetailState.Success(movieDetailDomainToStateModel.map(movieResult.data))
                    }
                    is NetworkResult.Error -> {
                        _detailErrorState.emit(movieResult.error)
                    }
                }
            }
        }
    }
}