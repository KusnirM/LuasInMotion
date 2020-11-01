package com.example.luasinmotionandroid.utils

fun String?.isNotNullOrEmpty(): Boolean {
    return this?.let {
        isNotEmpty()
    } ?: false
}

fun String?.isAnInt(): Boolean {
    return try {
        this ?: return false
        val value = toIntOrNull()
        value != null
    } catch (ex: IllegalArgumentException) {
        false
    }
}
