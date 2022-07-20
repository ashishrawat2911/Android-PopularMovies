package com.example.android_popularmovies.presentation.movie.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_popularmovies.databinding.MovieViewBinding
import com.example.android_popularmovies.presentation.movie.state.MovieStateData

class MoviesAdapter(private val onItemTap: (id: Int) -> Unit) :
    RecyclerView.Adapter<MoviesViewHolder>() {

    private val movies = mutableListOf<MovieStateData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: MovieViewBinding =
            MovieViewBinding.inflate(layoutInflater, parent, false)
        return MoviesViewHolder(itemBinding, onItemTap)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(movies[position])
    }

    fun updateMovies(movies: List<MovieStateData>) {
        val diffCallback = MovieDiffCallback(this.movies, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        this.movies.clear()
        this.movies.addAll(movies)
    }
}
