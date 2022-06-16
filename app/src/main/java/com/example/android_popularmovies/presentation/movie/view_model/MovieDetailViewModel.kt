package com.example.android_popularmovies.presentation.movie.view_model

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.analytics.MovieAnalytics
import com.example.android_popularmovies.AppDispatchers
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.usecase.GetMovieBelongingsUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieBelongingState
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.utils.ResultState
import com.example.android_popularmovies.utils.getMovieErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesUseCase: GetMovieDetailsUseCase,
    private val getMovieBelongingsUseCase: GetMovieBelongingsUseCase,
    private val movieAnalytics: MovieAnalytics,
    private val appDispatchers: AppDispatchers

) : ViewModel() {
    var movieDetailState = MovieDetailState(ResultState.Init());
    var movieBelongingState = MovieBelongingState(ResultState.Init());

    fun detailState(): LiveData<MovieDetailState> = _detailState
    private val _detailState = MutableLiveData<MovieDetailState>()

    val belongingState: LiveData<MovieBelongingState> get() = _belongingState
    private val _belongingState = MutableLiveData<MovieBelongingState>()

    init {
        _detailState.value = movieDetailState.copy(movieResultState = ResultState.Init())
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch(Dispatchers.Main) {
            _detailState.value =
                movieDetailState.copy(
                    movieResultState = ResultState.Error(
                        exception.fillInStackTrace().getMovieErrorMessage()
                    )
                )

            val bundle = Bundle()
            bundle.putString("MovieDetailFetch", "Passed")
            movieAnalytics.logEvent("MovieDetailFetch", bundle)
        }
    }

    fun getMovieDetails(movieId: Int) =
        viewModelScope.launch(appDispatchers.IO + exceptionHandler) {
            _detailState.value = movieDetailState.copy(movieResultState = ResultState.Loading())
            val response = async { getMoviesUseCase(movieId) }
            val value =
                movieDetailState.copy(movieResultState = ResultState.Success((response.await()).toState()))
            withContext(appDispatchers.Main) {
                _detailState.value = value
            }
            getMovieBelongings(movieId)
        }

    fun getMovieBelongings(movieId: Int) = viewModelScope.launch {
        _belongingState.value =
            movieBelongingState.copy(movieResultState = ResultState.Loading())

        getMovieBelongingsUseCase(movieId)
            .onStart { }
            .catch {
                _belongingState.value =
                    movieBelongingState.copy(movieResultState = ResultState.Error(it.getMovieErrorMessage()))
                Timber.e(this.toString())
            }
            .collect {
                _belongingState.value =
                    movieBelongingState.copy(movieResultState = ResultState.Success(it.map { it.toState() }))
            }
    }

}
