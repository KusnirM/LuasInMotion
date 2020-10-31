package com.example.luasinmotionandroid.presentation.di

import com.example.luasinmotionandroid.presentation.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

// presentation layer -> viewmodels, mappers
val presentationModule = module {

    viewModel { MainViewModel(get()) }
}
