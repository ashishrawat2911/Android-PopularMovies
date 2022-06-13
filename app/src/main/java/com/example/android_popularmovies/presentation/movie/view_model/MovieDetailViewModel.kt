package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.usecase.GetMovieBelongingsUseCase
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var job: Job? = null
    fun getMovieDetails(movieId: Int) {
        movieDetailsState.value = ResultState.Loading()
        job = viewModelScope.launch() {
            val response = getMoviesUseCase(movieId)
            launch {
                movieDetailsState.value = response.let { ResultState.Success(it) }
            }
        }
        job?.invokeOnCompletion {
            viewModelScope.launch {
                movieDetailsState.value =
                    it?.toString()?.let { it1 -> ResultState.Error(it1) }
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

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}
