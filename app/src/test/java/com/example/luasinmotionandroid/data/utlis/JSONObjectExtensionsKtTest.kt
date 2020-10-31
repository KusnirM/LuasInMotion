package com.example.luasinmotionandroid.data.utlis

import org.joda.time.DateTime
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test

class JSONObjectExtensionsKtTest {
    private val created = "2020-10-31T12:55:50"
    private val key = "created"

    @Test
    fun `should get correct dateTime from json object`() {
        val expected = DateTime(created)
        val obj = JSONObject(json)
        val actual = obj.getDateTime(key)
        assertEquals(expected, actual)
    }

    private val json =
        """
{ 
    "$key": "$created",
}
        """.trimIndent()
}
