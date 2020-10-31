package com.example.luasinmotionandroid.presentation.ui.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity(), BaseFragment.Callback {

    @Nullable
    var lifecycleOwner: ViewLifecycleOwner? = null

    override fun onNavigateTo(activity: Class<out BaseActivity>, intentDetails: Intent.() -> Unit) {
        navigateTo(activity, intentDetails)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleOwner = ViewLifecycleOwner()
        lifecycleOwner?.lifecycle?.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        super.onCreate(savedInstanceState)
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

    fun navigateTo(activity: Class<out BaseActivity>, intentDetails: Intent.() -> Unit = {}) {
        startActivity(
            Intent(this, activity).also {
                it.intentDetails()
            }
        )
    }

    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, length).show()

    private fun handleError(ex: Exception) = Timber.d(ex)

    class ViewLifecycleOwner : LifecycleOwner {

        private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

        val lifecycle: LifecycleRegistry
            get() = lifecycleRegistry

        override fun getLifecycle(): Lifecycle = lifecycleRegistry
    }
}
