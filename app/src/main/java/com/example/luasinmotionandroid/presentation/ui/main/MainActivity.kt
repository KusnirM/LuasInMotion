package com.example.luasinmotionandroid.presentation.ui.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.luasinmotionandroid.R
import com.example.luasinmotionandroid.presentation.customViews.DialogFactory
import com.example.luasinmotionandroid.presentation.model.GreenLine
import com.example.luasinmotionandroid.presentation.ui.base.BaseActivity
import com.example.luasinmotionandroid.presentation.ui.main.adapter.TramAdapter
import com.example.luasinmotionandroid.presentation.utils.safelyObserve
import com.example.luasinmotionandroid.utils.isNotNullOrEmpty
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

/**
 * as you can see here is really no logic, just setting up adapter, observing viewmodel states and setting up the ui
 */

class MainActivity : BaseActivity() {

    private val vm by inject<MainViewModel>()
    private lateinit var tramAdapter: TramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViews()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        vm.onResume()
    }

    private fun setViews() {
        tramAdapter = TramAdapter()
        tramList.adapter = tramAdapter

        getItems.setOnClickListener {
            vm.getUpdates()
        }

        clearItems.setOnClickListener {
            tramAdapter.clear()
            message.isVisible = false
        }
    }

    private fun setupObservers() {
        vm.greenLineLoader.safelyObserve(
            lifecycleOwner,
            Observer {
                progressBar.isVisible = it
            }
        )
        vm.mainState.safelyObserve(
            lifecycleOwner,
            Observer {
                when (it) {
                    is MainState.GetGreenlineState.Success -> displayGreenLineSuccess(it.greenLine)
                    is MainState.GetGreenlineState.Error -> displayGreenLineError(it.errorDisplay)
                }
            }
        )
    }

    private fun displayGreenLineSuccess(greenLine: GreenLine) {
        val msg = greenLine.message
        message.isVisible = msg.isNotNullOrEmpty()
        message.text = msg

        // advantage of setting it with enum , with list we will need to add up mapping all the time
        // but with enum we loose flexibility over api changes

        // 		<tram destination="No Northbound Service" dueMins="" />
        // according to this result i assume list should never be empty and we should display this as 1 item

        stopTitle.text = getString(greenLine.datePart.stop.displayName)
        directionTitle.text = getString(greenLine.datePart.direction.displayName)

        val tramList = greenLine.tramList
        tramAdapter.setItems(tramList)
    }

    private fun displayGreenLineError(message: String) {
        DialogFactory.showMessage(
            context = this,
            title = "Error Title", // question on design team
            body1 = message
        )
    }
}
