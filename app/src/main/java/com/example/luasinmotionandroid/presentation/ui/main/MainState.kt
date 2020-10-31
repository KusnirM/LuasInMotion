package com.example.luasinmotionandroid.presentation.ui.main

import com.example.luasinmotionandroid.presentation.model.GreenLine

sealed class MainState {

    sealed class InboundState : MainState() {
        class Success(val greenLine: GreenLine) : InboundState()
        class Error(val errorDisplay: String) : InboundState()
    }

    sealed class OutboundState : MainState() {
        class Success(val greenLine: GreenLine) : OutboundState()
        class Error(val errorDisplay: String) : OutboundState()
    }
}
