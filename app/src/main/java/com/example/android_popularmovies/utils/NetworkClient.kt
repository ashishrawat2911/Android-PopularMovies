package com.example.android_popularmovies.utils

import com.example.android_popularmovies.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient {

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()

    fun getRetrofit(
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.apiBaseUrl)
            .addConverterFactory(gsonConverterFactory())
            .client(httpClient())
            .build()
    }

    private fun httpClient(): OkHttpClient {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val interceptor = Interceptor { chain ->
            val url =
                chain.request().url.newBuilder().addQueryParameter(
                    Constants.apiKey,
                    BuildConfig.movieApiKey
                )
                    .build()
            val request = chain.request().newBuilder().url(url).build()

            return@Interceptor chain.proceed(request)
        }
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(logging)
            .connectTimeout(Constants.connectTimeout, TimeUnit.SECONDS)
            .build()
    }


    private fun gsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}