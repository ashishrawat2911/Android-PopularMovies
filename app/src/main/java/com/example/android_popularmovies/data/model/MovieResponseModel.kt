package com.example.android_popularmovies.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponseModel(
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