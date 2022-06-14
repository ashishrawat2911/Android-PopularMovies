package com.example.android_popularmovies.presentation.movie.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_popularmovies.domain.mapper.toState
import com.example.android_popularmovies.domain.usecase.GetMoviesUseCase
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.utils.ResultState
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
    var movieState = MovieListState(ResultState.Init());
    val state: LiveData<MovieListState> get() = mState
    private val mState = MutableLiveData<MovieListState>()
    private var disposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    init {
//        mState.value = movieState.copy(
//            movieResultState = ResultState.Loading()
//        )
        loadMovies()
    }

    fun loadMovies() {
        disposable = getMoviesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    mState.value =
                        movieState.copy(movieResultState = ResultState.Success(it.map { it.toState() }))
                }
            }, {
                mState.value =
                    movieState.copy(movieResultState = ResultState.Error(it.getMovieErrorMessage()))
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
