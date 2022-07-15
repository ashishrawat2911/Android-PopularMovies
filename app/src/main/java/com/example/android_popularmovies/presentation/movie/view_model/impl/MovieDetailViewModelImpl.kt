package com.example.android_popularmovies.presentation.movie.view_model.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.mapper.MovieEntityToStateMapper
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.presentation.movie.view_model.MovieDetailViewModel
import com.example.android_popularmovies.utils.AppDispatchers
import com.example.android_popularmovies.utils.getMovieErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModelImpl @Inject constructor(
    private val getMoviesUseCase: GetMovieDetailsUseCase,
    private val appDispatchers: AppDispatchers,
    private val movieEntityToStateMapper: MovieEntityToStateMapper
) : ViewModel(), MovieDetailViewModel {
    override val detailState: StateFlow<MovieDetailState> get() = _detailState
    private val _detailState =
        MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)

    override val detailErrorState: SharedFlow<String> get() = _detailErrorState
    private val _detailErrorState =
        MutableSharedFlow<String>()

    override fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            getMoviesUseCase(movieId).catch { exception ->
                _detailState.value = MovieDetailState.Error(exception.fillInStackTrace().getMovieErrorMessage())
                    _detailErrorState.emit(exception.fillInStackTrace().getMovieErrorMessage())
            }.collectLatest {
                withContext(appDispatchers.Main) {
                    _detailState.value = MovieDetailState.Success(movieEntityToStateMapper.map(it))
                }
            }
        }
    }


    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}