package com.example.luasinmotionandroid.data.utlis

import okhttp3.HttpUrl

fun HttpUrl.Builder.addQueryParams(params: Map<String, String?>): HttpUrl.Builder {
    params.forEach { (key, value) ->
        value?.let { safeValue ->
            addQueryParameter(key, safeValue)
        }
    }
    return this
}
