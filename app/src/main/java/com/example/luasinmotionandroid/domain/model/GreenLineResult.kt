package com.example.luasinmotionandroid.domain.model

import androidx.annotation.StringRes
import com.example.luasinmotionandroid.R
import com.example.luasinmotionandroid.data.model.Stop
import org.joda.time.DateTime
import timber.log.Timber

data class GreenLineResult(
    val stopInfo: StopInfo? = null,
    val errorResponse: ErrorResponse? = null
)





