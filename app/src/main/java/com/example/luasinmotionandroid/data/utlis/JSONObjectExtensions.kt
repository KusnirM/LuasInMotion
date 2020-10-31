package com.example.luasinmotionandroid.data.utlis

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.json.JSONObject

fun JSONObject.getDateTime(key: String, format: String? = null): DateTime {
    return when (format) {
        null -> DateTime.parse(getString(key))
        else -> DateTime.parse(getString(key), DateTimeFormat.forPattern(format))
    }
}
