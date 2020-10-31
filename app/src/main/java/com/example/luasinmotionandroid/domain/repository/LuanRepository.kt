package com.example.luasinmotionandroid.domain.repository

import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.domain.model.GreenLineResult

interface LuanRepository {

    suspend fun getGreenLine(stop: Stop): GreenLineResult
}
