package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.ResultState
import com.example.android_popularmovies.utils.getMovieErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {
    val state: LiveData<ResultState<List<MovieEntity>>> get() = movieState
    private val movieState = MutableLiveData<ResultState<List<MovieEntity>>>()
    private var disposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    init {
        movieState.value = ResultState.Init()
        movieState.value = ResultState.Loading()
        loadMovies()
    }

    fun loadMovies() {
        disposable = getMoviesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    movieState.value = ResultState.Success(it)
                }
            }, {
                movieState.value = ResultState.Error(it.getMovieErrorMessage())
            })

        disposable?.let {
            compositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
