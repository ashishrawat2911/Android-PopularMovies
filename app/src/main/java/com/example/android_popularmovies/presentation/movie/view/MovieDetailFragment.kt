package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android_popularmovies.databinding.MovieDetailFragmentBinding
import com.example.android_popularmovies.presentation.movie.state.MovieDetailState
import com.example.android_popularmovies.presentation.movie.state.MovieDetailStateData
import com.example.android_popularmovies.presentation.movie.view_model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    companion object {
        const val movieImagePath: String = "https://image.tmdb.org/t/p/w500"
    }

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        initialize()
        return binding.root
    }


    private fun initialize() {
        setUpViewModel()
        handleUIResult()
        handleErrorToast()
    }


    private fun setUpViewModel() {
        val args: MovieDetailFragmentArgs by navArgs()
        viewModel.getMovieDetails(args.movieId)
    }

    private fun handleErrorToast() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailErrorState.collectLatest {
                    Toast.makeText(
                        activity,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun handleUIResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        is MovieDetailState.Error -> {
                            binding.progressBar.hideVisibility()
                            Timber.e(it.error)
                        }
                        is MovieDetailState.Loading -> {
                            binding.progressBar.showVisibility()
                        }
                        is MovieDetailState.Success -> {
                            binding.progressBar.hideVisibility()
                            updateMovieUI(it.movie)
                        }
                    }
                }
            }
        }
    }

    private fun updateMovieUI(movie: MovieDetailStateData) {
        with(binding) {
            movieTitle.text = movie.detailTitle
            movieOverview.text = movie.detailOverview
            movieRating.text = movie.detailVoteAverage.toString()
            Glide.with(requireActivity())
                .load("${movieImagePath}${movie.detailBackdropPath}")
                .into(moviePhoto)
            movieOverview.text = movie.detailOverview
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
