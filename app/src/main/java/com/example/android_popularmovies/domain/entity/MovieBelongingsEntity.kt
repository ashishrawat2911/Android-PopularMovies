package com.example.android_popularmovies.domain.entity

data class MovieBelongingsEntity(
    val description: String,
    val favoriteCount: Long,
    val id: Long,
    val itemCount: Long,
    val name: String,
    val posterPath: Any? = null
)