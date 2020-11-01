package com.example.luasinmotionandroid.domain.model

import com.example.luasinmotionandroid.data.model.Stop
import org.joda.time.DateTime

data class StopInfo(
    val created: DateTime,
    val stop: Stop,
    val message: String = "",
    val directionList: List<Direction> = emptyList()
)