package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_popularmovies.databinding.MovieListFragmentBinding
import com.example.android_popularmovies.presentation.movie.adaptor.MoviesAdapter
import com.example.android_popularmovies.presentation.movie.state.MovieListState
import com.example.android_popularmovies.presentation.movie.view_model.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels()
    private var _binding: MovieListFragmentBinding? = null
    private val binding get() = _binding!!
    private var moviesAdapter: MoviesAdapter = MoviesAdapter {
        viewModel.onDetailTap(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    private fun initialize() {
        setRecyclerView()
        fetchMovies()
        handleError()
        handleResult()
        handleOnDetailTap()
    }

    private fun handleOnDetailTap() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onTapDetailState.collectLatest {
                    val navController = binding.root.findNavController()
                        navController
                            .navigate(
                                MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                                    it
                                )
                            )
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, movieGridSpanCount)
        }
        setUpSearch(moviesAdapter)
    }

    private fun fetchMovies() {
        viewModel.fetchMoviesList()
    }

    private fun setUpSearch(adapter: MoviesAdapter) {
        binding.searchBar.addTextChangedListener {
            it?.run {
                adapter.updateMovies(viewModel.filterMovies(it.toString()))
            }
        }
    }

    private fun handleError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorState.collect {
                    Toast.makeText(
                        activity,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun handleResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is MovieListState.Error -> {
                            binding.progressBar.hideVisibility()
                            Timber.e(it.error)
                        }
                        MovieListState.Loading -> {
                            binding.progressBar.showVisibility()
                        }
                        is MovieListState.Success -> {
                            binding.progressBar.hideVisibility()
                            moviesAdapter.updateMovies(it.movies)
                        }
                    }
                    binding.recyclerView.adapter = moviesAdapter
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val movieGridSpanCount: Int = 2
    }
}
