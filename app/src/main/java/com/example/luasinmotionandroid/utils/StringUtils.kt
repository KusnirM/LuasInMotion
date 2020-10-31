package com.example.luasinmotionandroid.utils

fun String?.isNotNullOrEmpty(): Boolean {
    return this?.let {
        isNotEmpty()
    } ?: false
}
