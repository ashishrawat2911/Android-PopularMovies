package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android_popularmovies.R
import com.example.android_popularmovies.databinding.MovieDetailFragmentBinding
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.presentation.movie.view_model.MovieDetailViewModel
import com.example.android_popularmovies.presentation.movie.view_model.impl.MovieDetailViewModelImpl
import com.example.android_popularmovies.utils.Constants
import com.example.android_popularmovies.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: MovieDetailFragmentBinding

    private val viewModel: MovieDetailViewModel by viewModels<MovieDetailViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.movie_detail_fragment, container, false
        )
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
        viewLifecycleOwner.lifecycleScope.launch {
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailState.collectLatest {
                    when (it.movieResultState) {
                        is ResultState.Loading->{
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.movie =
                                (it.movieResultState as ResultState.Success<MovieStateData>).result
                            activity?.let { it1 ->
                                Glide.with(it1)
                                    .load("${Constants.movieImagePath}${(it.movieResultState as ResultState.Success<MovieStateData>).result.posterPath}")
                                    .into(binding.moviePhoto)
                            }
                        }
                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Timber.e((it.movieResultState as ResultState.Error<MovieStateData>).message)
                        }
                        else -> {}
                    }
                }

            }
        }
    }
}
