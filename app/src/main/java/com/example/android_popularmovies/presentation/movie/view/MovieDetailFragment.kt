package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android_popularmovies.R
import com.example.android_popularmovies.databinding.MovieDetailFragmentBinding
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.presentation.movie.view_model.MovieDetailViewModel
import com.example.android_popularmovies.utils.Constants
import com.example.android_popularmovies.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: MovieDetailFragmentBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<MovieDetailFragmentBinding>(
            inflater, R.layout.movie_detail_fragment, container, false
        )
        setUpViewModel()
        return binding.root
    }

    private fun setUpViewModel() {
        val args: MovieDetailFragmentArgs by navArgs()
        viewModel.getMovieDetails(args.movieId)
        viewModel.detailState().observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (it.movieResultState is ResultState.Loading) View.VISIBLE else View.GONE
            when (it.movieResultState) {
                is ResultState.Success -> {
                    binding.movie =
                        (it.movieResultState as ResultState.Success<MovieStateData>).result
                    activity?.let { it1 ->
                        Glide.with(it1)
                            .load("${Constants.movieImagePath}${(it.movieResultState as ResultState.Success<MovieStateData>).result.posterPath}")
                            .into(binding.moviePhoto)
                    }
                }
                is ResultState.Error -> {
                    Timber.e((it.movieResultState as ResultState.Error<MovieStateData>).message)
                    Toast.makeText(
                        activity,
                        (it.movieResultState as ResultState.Error<MovieStateData>).message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }
}
