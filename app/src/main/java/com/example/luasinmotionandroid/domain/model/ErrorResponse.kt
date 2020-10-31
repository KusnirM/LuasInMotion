package com.example.luasinmotionandroid.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @Transient
    val throwable: Throwable? = null,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("httpStatusCode")
    val httpStatusCode: Int = 0
) {
    override fun toString(): String {
        return throwable?.let { throwable.message } ?: "$httpStatusCode"
    }
}
