package com.example.android_popularmovies.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.example.android_popularmovies.R
import com.example.android_popularmovies.presentation.movie.view.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycle.coroutineScope.launch {
            delay(DELAY_IN_IN_TRANSITION)
            navigate()
        }
    }

    private fun navigate() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        private const val DELAY_IN_IN_TRANSITION = 2000L
    }
}
