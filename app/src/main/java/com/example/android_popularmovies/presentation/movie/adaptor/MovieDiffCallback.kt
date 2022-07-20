package com.example.android_popularmovies.presentation.movie.adaptor

import androidx.recyclerview.widget.DiffUtil
import com.example.android_popularmovies.presentation.movie.state.MovieStateData

class MovieDiffCallback(
    private val oldMovies: List<MovieStateData>,
    private val newNewMovies: List<MovieStateData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldMovies.size
    }

    override fun getNewListSize(): Int {
        return newNewMovies.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition].id == newNewMovies[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition] == newNewMovies[newItemPosition]
    }
}
