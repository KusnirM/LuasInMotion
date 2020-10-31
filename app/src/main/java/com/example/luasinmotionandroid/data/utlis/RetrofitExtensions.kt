package com.example.luasinmotionandroid.data.utlis

import retrofit2.Response

fun <T : Any> Response<T>.getEtag() = this.headers()["etag"] ?: ""
