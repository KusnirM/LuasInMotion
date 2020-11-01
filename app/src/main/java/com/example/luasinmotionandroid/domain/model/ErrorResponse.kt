package com.example.luasinmotionandroid.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @Transient
    val throwable: Throwable? = null,
    @SerializedName("error")
    val error: String? = null,
    val errorDisplay: String?, // = "DEFAULT ERROR DISPLAY USER WILL SEE",
    @SerializedName("httpStatusCode")
    val httpStatusCode: Int = 0
) : Exception(error) {
    override fun toString(): String {
        return "msg: ${throwable?.message ?: ""}, code: $httpStatusCode"
    }
}
