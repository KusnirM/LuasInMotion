package com.example.luasinmotionandroid.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsKtTest {

    @Test
    fun `when string null should return false`() {
        var s: String? = null
        assertEquals(false, s.isNotNullOrEmpty())

        s = ""
        assertEquals(false, s.isNotNullOrEmpty())

        s = "a"
        assertEquals(true, s.isNotNullOrEmpty())

        s = " " // is still empty but not blank!
        assertEquals(true, s.isNotNullOrEmpty())
    }
}
