package com.example.android_popularmovies.presentation.movie.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_popularmovies.databinding.MovieViewBinding
import com.example.android_popularmovies.presentation.movie.state.MovieStateData

class MoviesViewHolder(private val binding: MovieViewBinding, val onItemTap: (id: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieStateData) {
        with(binding) {
            movieOverview.text = movie.overview
            movieRating.text = movie.voteAverage.toString()
            movieOverview.text = movie.overview
            movieTitle.text = movie.title
            Glide.with(itemView.context).load("${movieImagePath}${movie.posterPath}")
                .into(moviePhoto)
            movieCard.setOnClickListener {
                onItemTap(movie.id)
            }
        }
    }

    private companion object {
        const val movieImagePath: String = "https://image.tmdb.org/t/p/w500"
    }
}
