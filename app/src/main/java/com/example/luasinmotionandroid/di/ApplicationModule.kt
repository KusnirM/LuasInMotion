package com.example.luasinmotionandroid.di

import android.content.Context
import android.content.SharedPreferences
import com.example.luasinmotionandroid.data.storage.preferences.GreenLinePreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {

    single<SharedPreferences>(named(GreenLinePreferences.FILE_NAME)) {
        androidContext().getSharedPreferences(GreenLinePreferences.FILE_NAME, Context.MODE_PRIVATE)
    }
    single { GreenLinePreferences(get(named(GreenLinePreferences.FILE_NAME)), get()) }
}
