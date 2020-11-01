package com.example.luasinmotionandroid

import android.app.Application
import com.example.luasinmotionandroid.data.di.dataModule
import com.example.luasinmotionandroid.domain.di.domainModule
import com.example.luasinmotionandroid.presentation.di.presentationModule
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Represent main application class
 * here are initialized libraries with app context
 * i split them into separate functions for better readability, part of separation of concerns.
 * Once we will need to replace library it will be much clearer this way
 */

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initTime()
        initCrashlytics()
        initAnalytics()
        initKoin()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun initTime() {
        JodaTimeAndroid.init(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                dataModule + listOf(
                    domainModule,
                    presentationModule
                )
            )
        }
    }

    private fun initAnalytics() {
        //
    }

    private fun initCrashlytics() {
        if (!BuildConfig.DEBUG) {
            // farbic is getting deprecated, hence firebase
        }
    }
}
