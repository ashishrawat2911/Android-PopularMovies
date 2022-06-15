package com.example.android_popularmovies.data.mapper

import com.example.android_popularmovies.data.source.local.model.MovieDbModel
import com.example.android_popularmovies.data.source.remote.model.MovieApiModel
import com.example.android_popularmovies.data.source.remote.model.MovieBelongingApiModel
import com.example.android_popularmovies.domain.entity.MovieBelongingsEntity
import com.example.android_popularmovies.domain.entity.MovieEntity


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

fun MovieBelongingApiModel.toEntity() = MovieBelongingsEntity(
    id = id,
    posterPath = posterPath,
    name = name,
    itemCount = itemCount,
    favoriteCount = favoriteCount,
    description = description,
)
