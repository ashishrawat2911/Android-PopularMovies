package com.example.android_popularmovies.presentation.movie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_popularmovies.R
import com.example.android_popularmovies.databinding.MovieListFragmentBinding
import com.example.android_popularmovies.presentation.movie.adaptor.MoviesAdapter
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.presentation.movie.view_model.MovieListViewModel
import com.example.android_popularmovies.utils.ResultState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var binding: MovieListFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.movie_list_fragment, container, false
        )
        setUpViewModel()
        return binding.root
    }

    private fun setUpViewModel() {
        viewModel.movieState.observe(
            viewLifecycleOwner
        ) {
            binding.progressBar.visibility =
                if (it.movieResultState is ResultState.Loading) View.VISIBLE else View.GONE
            when (it.movieResultState) {
                is ResultState.Error -> {
                    Toast.makeText(activity, (it.movieResultState as ResultState.Error<List<MovieStateData>>).message, Toast.LENGTH_LONG).show()
                }
                is ResultState.Success -> {
                    setRecyclerView((it.movieResultState as ResultState.Success<List<MovieStateData>>).result)
                }
                else -> Unit
            }
        }
    }

    private fun setRecyclerView(list: List<MovieStateData>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = MoviesAdapter(list)
        }
    }
}
