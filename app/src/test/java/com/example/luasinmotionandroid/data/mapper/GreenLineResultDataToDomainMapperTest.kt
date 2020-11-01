package com.example.luasinmotionandroid.data.mapper

import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.StopInfo
import com.example.luasinmotionandroid.domain.model.Tram
import org.joda.time.DateTime
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GreenLineResultDataToDomainMapperTest {

    private lateinit var mapper: GreenLineResultDataToDomainMapper

    @Before
    fun setUp() {
        mapper = GreenLineResultDataToDomainMapper()
    }

    @Test
    fun `should throw illegal argument exception when body starts with Exception string`() {
        var s = "Exception: and some text"
        assertEquals(true, mapper.shouldThrowIllegalParamException(s))

        s = "Exception"
        assertEquals(true, mapper.shouldThrowIllegalParamException(s))

        s = "exception"
        assertEquals(true, mapper.shouldThrowIllegalParamException(s))
    }

    // todo should be extracted values, then applied to json and expected objects
    @Test
    fun `should mapp json object correctly`() {
        val actual = mapper.getStopInfo(JSONObject(responseJson))
        val expected = StopInfo(
            created = DateTime("2020-10-31T12:55:50"),
            stop = Stop.fromJson("Stillorgan"),
            message = "Green Line services operating normally",
            directionList = listOf(
                Direction(
                    name = Direction.Name.fromJson("Inbound"),
                    tramList = listOf(
                        Tram("2", "Broombridge"),
                        Tram("7", "Parnell"),
                        Tram("17", "Broombridge")
                    )
                ),
                Direction(
                    name = Direction.Name.fromJson("Outbound"),
                    tramList = listOf(
                        Tram("1", "Bride's Glen"),
                        Tram("9", "Sandyford"),
                        Tram("16", "Bride's Glen")
                    )
                )
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `should be able to get direction and tram list also from JSONObject, not just JSONArray`() {
        val actual = mapper.getStopInfo(JSONObject(jsonwithObects))
        val expected = StopInfo(
            created = DateTime("2020-11-01T12:37:42"),
            stop = Stop.fromJson("Stillorgan"),
            message = "Green Line services operating normally",
            directionList = listOf(
                Direction(
                    name = Direction.Name.fromJson("Outbound"),
                    tramList = listOf(
                        Tram("9", "Bride's Glen")
                    )
                )
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `when handleDueMins cant be converted to int should return empty string`() {
        val mins = "as"
        val actual = mapper.handleDueMins(mins)
        assertEquals("", actual)
    }

    @Test
    fun `due mins should handle negative number and keep it as empty string`() {
        val mins = "-1"
        val actual = mapper.handleDueMins(mins)
        assertEquals("", actual)
    }

    val jsonwithObects =
        """
{
    "stop": "Stillorgan",
    "created": "2020-11-01T12:37:42",
    "message": "Green Line services operating normally",
    "stopAbv": "STILLORGAN",
    "direction": {
        "name": "Outbound",
        "tram": {
          "destination": "Bride's Glen",
          "dueMins": "9"
        }
    }
}
        """.trimIndent()

    val responseJson =
        """{
  "stop": "Stillorgan",
  "created": "2020-10-31T12:55:50",
  "message": "Green Line services operating normally",
  "stopAbv": "STI",
  "direction": [
    {
      "name": "Inbound",
      "tram": [
        {
          "destination": "Broombridge",
          "dueMins": "2"
        },
        {
          "destination": "Parnell",
          "dueMins": "7"
        },
        {
          "destination": "Broombridge",
          "dueMins": "17"
        }
      ]
    },
    {
      "name": "Outbound",
      "tram": [
        {
          "destination": "Bride's Glen",
          "dueMins": "1"
        },
        {
          "destination": "Sandyford",
          "dueMins": "9"
        },
        {
          "destination": "Bride's Glen",
          "dueMins": "16"
        }
      ]
    }
  ]
}
        """.trimIndent()
}
