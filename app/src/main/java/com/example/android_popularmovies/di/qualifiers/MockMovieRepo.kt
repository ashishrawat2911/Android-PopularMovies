package com.example.android_popularmovies.di.qualifiers

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class MockMovieRepoQualifier()

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class MovieRepoQualifier()
