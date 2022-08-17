package com.example.android_popularmovies.di

import com.example.android_popularmovies.BuildConfig
import com.example.android_popularmovies.data.source.remote.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()

    private companion object {
        const val apiKey: String = "api_key"
        const val connectTimeout: Long = 60
        const val apiVersion: Int = 3
        const val apiBaseUrl = "https://api.themoviedb.org/$apiVersion/"
    }

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val interceptor = Interceptor { chain ->
            val url =
                chain.request().url.newBuilder().addQueryParameter(
                    apiKey,
                    BuildConfig.movieApiKey
                )
                    .build()
            val request = chain.request().newBuilder().url(url).build()

            return@Interceptor chain.proceed(request)
        }
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(logging)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
     fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Provides
    fun provideService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}
