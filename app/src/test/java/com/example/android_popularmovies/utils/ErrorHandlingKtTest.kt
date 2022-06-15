package com.example.android_popularmovies.utils

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ErrorHandlingKtTest {
    @Mock
    lateinit var t: Throwable

    @Test
    fun checkError() {
        Mockito.`when`(t.getMovieErrorMessage()).thenReturn("Error")
        assert(t.getMovieErrorMessage() == "Error")
    }
}
