package com.example.android_popularmovies.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class MovieListApiModel(
    @SerializedName("page")
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("results")
    val results: List<MovieApiModel>,
)

data class MovieApiModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")

    val backdropPath: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("overview")
    val overview: String,
)

