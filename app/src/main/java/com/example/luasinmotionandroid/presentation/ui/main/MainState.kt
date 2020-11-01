package com.example.luasinmotionandroid.presentation.ui.main

import com.example.luasinmotionandroid.presentation.model.GreenLine

sealed class MainState {

    sealed class GetGreenlineState : MainState() {
        class Success(val greenLine: GreenLine) : GetGreenlineState()
        class Error(val errorDisplay: String) : GetGreenlineState()
    }
}
