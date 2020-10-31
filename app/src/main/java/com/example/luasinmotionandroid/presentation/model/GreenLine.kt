package com.example.luasinmotionandroid.presentation.model

import com.example.luasinmotionandroid.domain.model.DatePart
import com.example.luasinmotionandroid.domain.model.Tram

data class GreenLine(
    val message: String?,
    val datePart: DatePart,
    val tramList: List<Tram>
)
