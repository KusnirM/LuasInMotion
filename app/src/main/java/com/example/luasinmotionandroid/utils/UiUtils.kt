package com.example.luasinmotionandroid.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.luasinmotionandroid.presentation.ui.base.BaseFragment

inline fun <reified T : BaseFragment> createFragment(
    noinline withArgs: Bundle.() -> Unit = {}
): BaseFragment {
    return T::class.java.newInstance().apply {
        arguments = Bundle().apply {
            withArgs()
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun <T> LiveData<T>.safelyObserve(lifecycleOwner: LifecycleOwner?, observer: Observer<T>) {
    lifecycleOwner?.let {
        observe(it, observer)
    }
}
