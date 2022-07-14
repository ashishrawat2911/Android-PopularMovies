package com.example.android_popularmovies.utils

interface Mapper<D, E> {
    fun map(type: D): E
}
