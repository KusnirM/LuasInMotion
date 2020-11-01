package com.example.luasinmotionandroid.data.utlis

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.junit.Assert.assertEquals
import org.junit.Test

class OkhttpUtilsKtTest {

    @Test
    fun `should add only not null params`() {
        val k1 = "sldj"
        val k3 = "lsdj"
        val params = mapOf(
            k1 to "v1",
            "k2" to null,
            k3 to "v3"
        )

        val actual = URL.toHttpUrlOrNull()
            ?.newBuilder()
            ?.addQueryParams(params)
            ?.build()
            ?.queryParameterNames

        val expected = setOf(k1, k3)
        assertEquals(expected, actual)
    }

    companion object {
        const val URL = "https://*.*"
    }
}
