package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_popularmovies.R
import com.example.android_popularmovies.databinding.MovieListFragmentBinding
import com.example.android_popularmovies.presentation.movie.adaptor.MoviesAdapter
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.presentation.movie.view_model.MovieListViewModel
import com.example.android_popularmovies.presentation.movie.view_model.impl.MovieListViewModelImpl
import com.example.android_popularmovies.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels<MovieListViewModelImpl>()

    private lateinit var binding: MovieListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.movie_list_fragment, container, false
        )
        initialize()
        return binding.root
    }

    private fun setUpSearch(adapter: MoviesAdapter) {
        binding.searchBar.addTextChangedListener {
            it?.let {
                viewModel.filterMovies(it.toString())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterState.collectLatest {
                    Timber.e("filter ${it.size}")
                    adapter.updateMovies(it);
                }
            }
        }

    }


    private fun initialize() {
        viewModel.fetchMoviesList()
        handleLoading()
        handleResult()
    }

    private fun handleResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect {
                    when (it.movieResultState) {
                        is ResultState.Error -> {
                            Toast.makeText(
                                activity,
                                (it.movieResultState as ResultState.Error<List<MovieStateData>>).message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is ResultState.Success -> {
                            setRecyclerView((it.movieResultState as ResultState.Success<List<MovieStateData>>).result)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun handleLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingState.collect {
                    binding.progressBar.visibility = it
                }
            }
        }
    }


    private fun setRecyclerView(list: List<MovieStateData>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            val moviesAdapter = MoviesAdapter(list)
            adapter = moviesAdapter
            setUpSearch(moviesAdapter)
        }
    }
}
