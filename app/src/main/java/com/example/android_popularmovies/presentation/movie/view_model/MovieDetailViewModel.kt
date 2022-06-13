package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.usecase.GetMovieBelongingsUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.ResultState
import com.example.android_popularmovies.utils.getMovieErrorMessage
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
    private val getMovieBelongingsUseCase: GetMovieBelongingsUseCase
) : ViewModel() {
    val state: LiveData<ResultState<MovieEntity>> get() = movieDetailsState
    private val movieDetailsState = MutableLiveData<ResultState<MovieEntity>>()

    init {
        movieDetailsState.value = ResultState.Init()
    }

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        viewModelScope.launch(Dispatchers.Main) {
            movieDetailsState.value =
                ResultState.Error(exception.fillInStackTrace().getMovieErrorMessage())
        }
    }

    fun getMovieDetails(movieId: Int) {
        movieDetailsState.value = ResultState.Loading()
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = async { getMoviesUseCase(movieId) }
            launch(Dispatchers.Main) {
                movieDetailsState.value = response.let { ResultState.Success(it.await()) }
            }
        }

        getMovieBelongings(movieId)
    }

    private fun getMovieBelongings(movieId: Int) {
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
