package com.example.android_popularmovies.data.source.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieListApiModel(
    @SerializedName("page")
    @Expose
    var page: Int? = null,
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null,
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null,
    @SerializedName("results")
    @Expose
    var results: List<MovieApiModel>? = null,
)

@Entity(tableName = "movies")
data class MovieApiModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null,
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null,
    @SerializedName("video")
    @Expose
    var video: Boolean? = null,
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = null,
    @SerializedName("overview")
    @Expose
    var overview: String? = null,
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,
//    @SerializedName("belongs_to_collection")
//    val belongsToCollection: BelongsToCollection,
    val budget: Long? = null,
//    val genres: List<Genre>,
//    val homepage: String,
//    @SerializedName("imdb_id")
//    val imdbID: String,
//    @SerializedName("production_companies")
//    val productionCompanies: List<ProductionCompany>,
//    @SerializedName("production_countries")
//    val productionCountries: List<ProductionCountry>,
    val revenue: Long? = null,
    val runtime: Long? = null,
//    @SerializedName("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>,
    val status: String? = null,
    val tagline: String? = null,
)

data class BelongsToCollection(
    val id: Long,
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String
)

data class Genre(
    val id: Long,
    val name: String
)

data class ProductionCompany(
    val id: Long,

    @SerializedName("logo_path")
    val logoPath: String,

    val name: String,

    @SerializedName("origin_country")
    val originCountry: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso3166_1: String,

    val name: String
)

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso639_1: String,

    val name: String
)
