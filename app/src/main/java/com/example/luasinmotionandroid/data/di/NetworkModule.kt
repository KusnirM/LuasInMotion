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

val networkModule = module {

        single { Gson() }
        single<Interceptor>(named("httpLogger")) {
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.BASIC
            )
        }

        single {
            Cache(androidContext().cacheDir, 10 * 1024 * 1024L)
        }

        single<OkHttpClient> {
            OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(get())
                .addInterceptor(get(named("httpLogger")))
                .build()
        }
}