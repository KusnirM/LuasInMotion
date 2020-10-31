package com.example.luasinmotionandroid.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun <T> LiveData<T>.safelyObserve(lifecycleOwner: LifecycleOwner?, observer: Observer<T>) {
    lifecycleOwner?.let {
        observe(it, observer)
    }
}
