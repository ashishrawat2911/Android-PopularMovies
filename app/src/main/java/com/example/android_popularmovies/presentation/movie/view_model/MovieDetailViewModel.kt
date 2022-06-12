package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_popularmovies.data.source.remote.model.Movie
import com.example.android_popularmovies.domain.usecase.GetMovieDetailsUseCase
import com.example.android_popularmovies.presentation.movie.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesUseCase: GetMovieDetailsUseCase,
    private val isNetworkAvailable: Boolean
) : ViewModel() {
    val state: LiveData<ResultState<Movie>> get() = movieDetailsState
    private val movieDetailsState = MutableLiveData<ResultState<Movie>>()

    init {
        movieDetailsState.value = ResultState.Init()
    }

    var job: Job? = null
    fun getMovieDetails(movieId: Int) {
        movieDetailsState.value = ResultState.Loading()
        job = viewModelScope.launch {
            if (isNetworkAvailable) {
                val response = getMoviesUseCase.getMovieDetail(movieId)
                if (response.isSuccessful) {
                    launch {
                        movieDetailsState.value = response.body()
                            ?.let { ResultState.Success(it) }
                    }
                }
            } else {
                launch(Dispatchers.IO) {
                    movieDetailsState.value =
                        ResultState.Success(getMoviesUseCase.getCacheMovie(movieId))
                }
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
            getMoviesUseCase.getMovieBelongings(movieId)
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