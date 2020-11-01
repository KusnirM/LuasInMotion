package com.example.luasinmotionandroid.data.mapper.base

import com.example.luasinmotionandroid.domain.model.ErrorResponse
import okhttp3.Response

open class BaseResponseMapper {

    open fun mapException(e: Exception, code: Int = -1): ErrorResponse {
        return ErrorResponse(
            throwable = e,
            errorDisplay = getErrorDisplay(code),
            httpStatusCode = code
        )
    }

    /**
     * just in case we would not get 200
     */

    fun mapUnsuccessful(response: Response): ErrorResponse? {
        val code = response.code()
        return try {
            ErrorResponse(
                error = response.message(),
                errorDisplay = getErrorDisplay(code),
                httpStatusCode = code
            )
        } catch (e: Exception) {
            mapException(e, code)
        }
    }

    fun getErrorDisplay(errorCode: Int = -1): String {
        return when (errorCode) {
            401 -> ErrorDisplay.E401
            // extract error displays

            else -> ErrorDisplay.DEFAULT
        }
    }

    object ErrorDisplay {
        const val DEFAULT = "Default error display" // to show user
        const val E401 = "401 user display error"
    }
}
