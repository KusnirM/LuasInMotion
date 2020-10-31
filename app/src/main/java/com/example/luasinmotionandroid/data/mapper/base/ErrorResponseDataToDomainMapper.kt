package com.example.luasinmotionandroid.data.mapper.base

import com.example.luasinmotionandroid.domain.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber

class ErrorResponseDataToDomainMapper(private val gson: Gson) {
    fun map(response: Response<*>): ErrorResponse {
        return try {
            var errorResponse = gson.fromJson(response.errorBody()!!.string(), ErrorResponse::class.java)
            errorResponse = errorResponse?.copy(httpStatusCode = response.code())
                ?: ErrorResponse(
                    error = response.raw().message(),
                    httpStatusCode = response.raw().code()
                )
            Timber.d("onResponse() -- generated ErrorResponse: ${errorResponse.error}")
            errorResponse
        } catch (t: Throwable) {
            ErrorResponse(
                throwable = t,
                httpStatusCode = response.code()
            )
        }
    }
}
