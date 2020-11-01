package com.example.luasinmotionandroid.domain.di

import com.example.luasinmotionandroid.data.mapper.GreenLineResultDataToDomainMapper
import com.example.luasinmotionandroid.data.mapper.base.BaseResponseMapper
import com.example.luasinmotionandroid.domain.usecase.greenLine.GetGreenLineUseCase
import org.koin.dsl.module

// use cases, mappers
val domainModule = module {

    // usecases
    single { GetGreenLineUseCase(get()) }

    // mappers

    // from response
    single { BaseResponseMapper() }
    single { GreenLineResultDataToDomainMapper() }
}
