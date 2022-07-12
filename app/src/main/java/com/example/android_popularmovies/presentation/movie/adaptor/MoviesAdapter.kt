package com.example.android_popularmovies.presentation.movie.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_popularmovies.databinding.MovieViewBinding
import com.example.android_popularmovies.presentation.movie.state.MovieStateData
import com.example.android_popularmovies.presentation.movie.view.MovieListFragmentDirections
import com.example.android_popularmovies.utils.Constants
import timber.log.Timber

class MoviesAdapter(private var movies: List<MovieStateData>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(), Filterable {
    private var filterMovies: List<MovieStateData> = movies
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: MovieViewBinding =
            MovieViewBinding.inflate(layoutInflater, parent, false)
        return MoviesViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return filterMovies.size
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(filterMovies[position])
    }

    private fun updateList(movies: List<MovieStateData>) {
        val diffCallback = MovieDiffCallback(this.movies, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        this.filterMovies = movies
    }


    fun filter(text: String?) {
        Timber.i(text)
        if (text != null) {
            val temp: MutableList<MovieStateData> = ArrayList()
            for (d in movies) {
                if (d.title.lowercase().contains(text.lowercase())) {
                    temp.add(d)
                }
            }
            updateList(temp)
        }
    }

    class MoviesViewHolder(private val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieStateData) {
            binding.movie = movie
            Glide.with(itemView.context).load("${Constants.movieImagePath}${movie.posterPath}")
                .into(binding.moviePhoto)
            binding.movieCard.setOnClickListener {
                it.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                        movie.id
                    )
                )
            }
        }
    }
}

