package com.example.luasinmotionandroid.data.mapper.base

import com.example.luasinmotionandroid.domain.model.ErrorResponse
import okhttp3.Response
import java.net.UnknownHostException

/**
 *As here is really just 1 response it gets bit tricky to decide what should belong here
 * By default it should be default error handling,
 * if other endpoints whould also use Xml converter, it would be good place to transform it to json over here
 */

open class BaseResponseMapper {

    open fun mapException(e: Exception, code: Int = -1): ErrorResponse {
        return ErrorResponse(
            throwable = e,
            errorDisplay = getErrorDisplay(e, code),
            httpStatusCode = code
        )
    }

    /**
     * just in case we would not get 200
     */
    fun mapUnsuccessful(response: Response): ErrorResponse? {
        val code = response.code
        return try {
            ErrorResponse(
                error = response.message,
                errorDisplay = getErrorDisplay(code),
                httpStatusCode = code
            )
        } catch (e: Exception) {
            mapException(e, code)
        }
    }

    private fun getErrorDisplay(e: Exception, code: Int): String {
        return when (e) {
            is UnknownHostException -> ErrorDisplay.UNKNOWN_HOST
            else -> getErrorDisplay()
        }
    }

    private fun getErrorDisplay(errorCode: Int = -1): String {
        return when (errorCode) {
            401 -> ErrorDisplay.E401
            // extract error displays

            else -> ErrorDisplay.DEFAULT
        }
    }

    object ErrorDisplay {
        const val DEFAULT = "Default error display" // to show user
        const val E401 = "401 user display error"
        const val UNKNOWN_HOST = "Unknown host, please check your internet connectivity and try again"
    }
}
