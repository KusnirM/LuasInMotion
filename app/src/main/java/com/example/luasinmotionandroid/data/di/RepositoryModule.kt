package com.example.luasinmotionandroid.data.di

import com.example.luasinmotionandroid.data.repository.LuanRepositoryImpl
import com.example.luasinmotionandroid.domain.repository.LuanRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<LuanRepository> { LuanRepositoryImpl(get(), get()) }
}
