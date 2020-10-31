package com.example.luasinmotionandroid.data.mapper

import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.StopInfo
import com.example.luasinmotionandroid.domain.model.Tram
import com.nhaarman.mockitokotlin2.mock
import org.joda.time.DateTime
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GreenLineResultDataToDomainMapperTest {

    private lateinit var mapper: GreenLineResultDataToDomainMapper

    @Before
    fun setUp() {
        mapper = GreenLineResultDataToDomainMapper(mock())
    }

    // todo should be extracted values, then applied to json and expected objects
    @Test
    fun `should mapp json object correctly`() {
        val actual = mapper.getStopInfo(JSONObject(responseJson))
        val expected = StopInfo(
            created = DateTime("2020-10-31T12:55:50"),
            stop = Stop.fromJson("Stillorgan"),
            message = "Green Line services operating normally",
            stopAbv = "STI",
            directionList = listOf(
                Direction(
                    name = Direction.Name.fromJson("Inbound"),
                    tramList = listOf(
                        Tram(2, "Broombridge"),
                        Tram(7, "Parnell"),
                        Tram(17, "Broombridge")
                    )
                ),
                Direction(
                    name = Direction.Name.fromJson("Outbound"),
                    tramList = listOf(
                        Tram(1, "Bride's Glen"),
                        Tram(9, "Sandyford"),
                        Tram(16, "Bride's Glen")
                    )
                )
            )
        )
        assertEquals(expected, actual)
    }

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
