package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieListApiModel
import com.example.android_popularmovies.domain.entity.MovieEntity
import com.example.android_popularmovies.domain.entity.MovieListEntity

fun MovieListApiModel.toEntity() = MovieListEntity(
    page = page,
    totalResults = totalResults,
    totalPages = totalPages,
    results = results?.map { it.toEntity() },
)

fun MovieApiModel.toEntity() = MovieEntity(
    id = id!!,
    title = title!!,
    backdropPath = backdropPath!!,
    posterPath = posterPath!!,
    overview = overview!!,
    voteAverage = voteAverage!!,
)

fun MovieDbModel.toEntity() = MovieEntity(
    id = id,
    title = title,
    backdropPath = backdropPath,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage,
)
fun MovieEntity.toDBModel() = MovieDbModel(
    id = id,
    title = title,
    backdropPath = backdropPath,
    posterPath = posterPath,
    overview = overview,
    voteAverage = voteAverage,
)
