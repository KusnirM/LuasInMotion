package com.example.luasinmotionandroid.data.di

import com.example.luasinmotionandroid.BuildConfig
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

/**
 * data layer module  -> db, shared prefs, networking
 */
val dataModule = listOf(
    repositoryModule,
    networkModule
)



