package com.example.android_popularmovies.data.source.remote.model

data class ErrorModel(
    val success: Boolean,
    val statusCode: Long,
    val statusMessage: String
)
