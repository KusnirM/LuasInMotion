package com.example.luasinmotionandroid.data.utlis

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.json.JSONObject
import timber.log.Timber

fun JSONObject.nullableString(keyName: String) = if (isNull(keyName)) null else getString(keyName)
fun JSONObject.nullableBoolean(keyName: String) = if (isNull(keyName)) null else getBoolean(keyName)

fun JSONObject.nullableInt(key: String) = if (isNull(key)) null else getInt(key)
fun JSONObject.nullableLong(key: String) = if (isNull(key)) null else getLong(key)

fun JSONObject.nullableDateTime(key: String, format: String?) =
    if (isNull(key)) null
    else {
        when (format) {
            null -> DateTime.parse(getString(key))
            else -> DateTime.parse(getString(key), DateTimeFormat.forPattern(format))
        }
    }

fun JSONObject.getDateTime(key: String, format: String? = null): DateTime {
    return when (format) {
        null -> DateTime.parse(getString(key))
        else -> DateTime.parse(getString(key), DateTimeFormat.forPattern(format))
    }
}

fun JSONObject.nullableDateTime(key: String, format: DateTimeFormatter? = null) =
    if (isNull(key)) null
    else {
        try {
            when (format) {
                null -> DateTime.parse(getString(key))
                else -> {
                    DateTime.parse(getString(key), format)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
