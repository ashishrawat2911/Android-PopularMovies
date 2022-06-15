package com.example.android_popularmovies.presentation.movie.view_model

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.usecase.GetMovieBelongingsUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.utils.ResultState
import com.example.android_popularmovies.utils.getMovieErrorMessage
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesUseCase: GetMovieDetailsUseCase,
    private val getMovieBelongingsUseCase: GetMovieBelongingsUseCase,
    private val firebaseAnalytics: FirebaseAnalytics

) : ViewModel() {
    var movieState = MovieDetailState(ResultState.Init());
    var movieBelongingState = MovieDetailState(ResultState.Init());

    val state: LiveData<MovieDetailState> get() = movieDetailsState
    private val movieDetailsState = MutableLiveData<MovieDetailState>()

    init {
        movieDetailsState.value = movieState.copy(movieResultState = ResultState.Init())
    }

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        viewModelScope.launch(Dispatchers.Main) {
            movieDetailsState.value =
                movieState.copy(
                    movieResultState = ResultState.Error(
                        exception.fillInStackTrace().getMovieErrorMessage()
                    )
                )

            val bundle = Bundle()
            bundle.putString("MovieDetailFetch", "Passed")
            firebaseAnalytics.logEvent("MovieDetailFetch", bundle)
        }
    }

    fun getMovieDetails(movieId: Int) {
        movieDetailsState.value = movieState.copy(movieResultState = ResultState.Loading())
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = async { getMoviesUseCase(movieId) }
            launch(Dispatchers.Main) {
                movieDetailsState.value =
                    movieState.copy(movieResultState = ResultState.Success((response.await()).toState()) )
            }
        }

        getMovieBelongings(movieId)
    }

     fun getMovieBelongings(movieId: Int) {
        viewModelScope.launch {
            getMovieBelongingsUseCase(movieId)
                .onStart { }
                .catch {
                    Timber.e(this.toString())
                }
                .collect {
                }
        }
    }

}
