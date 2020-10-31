package com.example.luasinmotionandroid.presentation.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

abstract class BaseFragment : Fragment() {

    private lateinit var baseFragmentCallback: Callback

    interface Callback {
        fun onNavigateTo(activity: Class<out BaseActivity>, intentDetails: Intent.() -> Unit)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callback) baseFragmentCallback = context
        else throw RuntimeException("$context must implement BaseFragment.Callback")
    }

    class ViewLifecycleOwner : LifecycleOwner {

        private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

        val lifecycle: LifecycleRegistry
            get() = lifecycleRegistry

        override fun getLifecycle(): Lifecycle = lifecycleRegistry
    }

    fun navigateTo(
        activity: Class<out BaseActivity>,
        intentDetails: Intent.() -> Unit = {}
    ) {
        baseFragmentCallback.onNavigateTo(activity, intentDetails)
    }

    fun toast(message: String) = context?.run { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

    @Nullable
    var lifecycleOwner: ViewLifecycleOwner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleOwner = ViewLifecycleOwner()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        lifecycleOwner = null
        super.onDestroy()
    }
}
