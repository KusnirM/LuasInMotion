package com.example.luasinmotionandroid.data.utlis

import okhttp3.HttpUrl

/**
 * Utils for http query to add params in more convenient way
 */
fun HttpUrl.Builder.addQueryParams(params: Map<String, String?>): HttpUrl.Builder {
    params.forEach { (key, value) ->
        value?.let { safeValue ->
            addQueryParameter(key, safeValue)
        }
    }
    return this
}
