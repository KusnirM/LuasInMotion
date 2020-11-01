package com.example.luasinmotionandroid.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luasinmotionandroid.domain.model.ErrorResponse
import com.example.luasinmotionandroid.domain.usecase.greenLine.GetGreenLineUseCase
import com.example.luasinmotionandroid.utils.currentTime

class MainViewModel(
    private val getGreenLineUseCase: GetGreenLineUseCase
) : ViewModel() {

    private val _greenLineLoader = MutableLiveData<Boolean>()
    val greenLineLoader: LiveData<Boolean> get() = _greenLineLoader

    private val _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState> get() = _mainState

    fun onResume() {
        getUpdates()
    }

    fun getUpdates() {
        _greenLineLoader.value = true
        getGreenLineUseCase.invoke(viewModelScope, currentTime()) {
            _greenLineLoader.value = false
            it.result(
                onSuccess = {
                    _mainState.value = MainState.GetGreenlineState.Success(it)
                },
                onFailure = {
                    (it as? ErrorResponse)?.run {
                        _mainState.value = MainState.GetGreenlineState.Error(it.errorDisplay ?: "")
                    }
                }
            )
        }
    }
}
