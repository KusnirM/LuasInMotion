package com.example.luasinmotionandroid.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsKtTest {

    @Test
    fun isNotNullOrEmpty() {
        var s: String? = null
        assertEquals(false, s.isNotNullOrEmpty())

        s = ""
        assertEquals(false, s.isNotNullOrEmpty())

        s = "a"
        assertEquals(true, s.isNotNullOrEmpty())

        s = " " // is still empty but not blank!
        assertEquals(true, s.isNotNullOrEmpty())
    }

    @Test
    fun `String isAnInt should return false if null`() {
        val s: String? = null
        assertEquals(false, s.isAnInt())
    }

    @Test
    fun `String isAnInt should return false if intOrNull returns null`() {
        val s = "intOrNull"
        assertEquals(false, s.isAnInt())
    }

    @Test
    fun `String isAnInt should return true if not null and intNotNull returns not a null`() {
        val s = "1"
//        val overMaxInt: String = "${Int.MAX_VALUE + 1L}"

        assertEquals(true, s.isAnInt())
    }

    /**
     * Small amusement below
     * I don't believe we will need to use toLong here, If i suppose to wait
     * more then 32bit minutes (2147483647) i assume there should already be something wrong :)))
     */

    @Test
    fun `String isAnInt should return false if int max value is overflown`() {
        val s = "${Int.MAX_VALUE + 1L}"
        assertEquals(false, s.isAnInt())
    }
}
