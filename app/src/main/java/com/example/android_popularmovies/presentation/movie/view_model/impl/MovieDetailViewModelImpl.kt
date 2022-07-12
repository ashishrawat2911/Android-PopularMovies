package com.example.android_popularmovies.presentation.movie.view_model.impl

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.mapper.MovieEntityToStateMapper
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.presentation.movie.view_model.MovieDetailViewModel
import com.example.android_popularmovies.utils.AppDispatchers
import com.example.android_popularmovies.utils.ResultState
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
    override val loadingState: StateFlow<Int>
        get() = _loadingState
    private val _loadingState =
        MutableStateFlow(View.GONE)
    override val detailState: StateFlow<MovieDetailState> get() = _detailState
    private val _detailState =
        MutableStateFlow(MovieDetailState(movieResultState = ResultState.Init()))

    override val detailErrorState: SharedFlow<String> get() = _detailErrorState
    private val _detailErrorState =
        MutableSharedFlow<String>()

    override fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(appDispatchers.IO) {
            val response = getMoviesUseCase(movieId)
            response.onStart {
                _detailState.value = MovieDetailState(
                    movieResultState = ResultState.Loading()
                )
                _loadingState.value = View.VISIBLE
            }.catch { exception ->
                _loadingState.value = View.GONE
                _detailState.value =
                    MovieDetailState(
                        movieResultState = ResultState.Error(
                            exception.fillInStackTrace().getMovieErrorMessage()
                        )
                    )
                viewModelScope.launch {
                    _detailErrorState.emit(exception.fillInStackTrace().getMovieErrorMessage())
                }
            }.collectLatest {
                withContext(appDispatchers.Main) {
                    _detailState.value = MovieDetailState(
                        movieResultState = ResultState.Success(
                            movieEntityToStateMapper.map(it)
                        )
                    )
                    _loadingState.value = View.GONE
                }
            }
        }
    }


    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}