package com.example.android_popularmovies.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class MovieListApiModel(
    @SerializedName("page")
    var page: Int,

    @SerializedName("total_results")
    var totalResults: Int,

    @SerializedName("total_pages")
    var totalPages: Int,

    @SerializedName("results")
    var results: List<MovieApiModel>,
)

data class MovieApiModel(
    @SerializedName("id")
    var id: Int,

    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("backdrop_path")

    var backdropPath: String,
    @SerializedName("original_language")

    var originalLanguage: String? = null,

    @SerializedName("title")
    var title: String,

    @SerializedName("vote_average")
    var voteAverage: Float,

    @SerializedName("overview")
    var overview: String,
)

