package com.example.android_popularmovies.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var posterPath: String,
    var backdropPath: String,
    var title: String,
    var voteAverage: Float,
    var overview: String,
)