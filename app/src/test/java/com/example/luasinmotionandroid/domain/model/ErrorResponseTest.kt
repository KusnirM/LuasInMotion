package com.example.luasinmotionandroid.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorResponseTest {

    @Test
    fun testToString() {
        val exMsg = "booom"
        val ex = Exception(exMsg)
        val code = 200
        val errorResponse = ErrorResponse(
            throwable = ex,
            error = "error",
            errorDisplay = "errorDisplay",
            httpStatusCode = code
        )
        val actual = "msg: $exMsg, code: $code"
        val expected = errorResponse.toString()
        assertEquals(actual, expected)
    }
}
