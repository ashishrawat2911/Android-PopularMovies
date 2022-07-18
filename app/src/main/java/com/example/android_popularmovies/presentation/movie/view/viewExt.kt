package com.example.android_popularmovies.presentation.movie.view

import android.view.View

fun View.handleVisibility(b: Boolean) {
    this.visibility = if (b) View.VISIBLE else View.GONE
}