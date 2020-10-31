package com.example.luasinmotionandroid.data.mapper

import com.example.luasinmotionandroid.data.mapper.base.ErrorResponseDataToDomainMapper
import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.data.utlis.getDateTime
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.ErrorResponse
import com.example.luasinmotionandroid.domain.model.GreenLineResult
import com.example.luasinmotionandroid.domain.model.StopInfo
import com.example.luasinmotionandroid.domain.model.Tram
import com.example.luasinmotionandroid.exceptions.EmptyResultBodyException
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.lang.NullPointerException

class GreenLineResultDataToDomainMapper(
    private val errorResponseDataToDomainMapper: ErrorResponseDataToDomainMapper
) {
    fun map(response: Response<StopInfo>): GreenLineResult {
        return when {
            response.isSuccessful -> {
                val body = response.body()
                body?.let {
                    GreenLineResult(
//                        etag = response.getEtag(),
//                        stopInfo = getStopInfo(it)
                    )

                    // extract into new exception_>
                }
                    ?: GreenLineResult(errorResponse = ErrorResponse(NullPointerException("empty body")))
            }

            else -> GreenLineResult(errorResponse = errorResponseDataToDomainMapper.map(response))
        }
    }

    fun map(response: String): GreenLineResult {
        val json = XmlToJson.Builder(response).build().toJson()
        val obj = json?.optJSONObject("stopInfo")
        return getStopInfo(obj)?.let {
            GreenLineResult(stopInfo = it)
        } ?: GreenLineResult(errorResponse = ErrorResponse(EmptyResultBodyException()))
    }

    fun getStopInfo(json: JSONObject?): StopInfo? {
        return json?.run {
            StopInfo(
                created = getDateTime("created"),
                stop = Stop.fromJson(getString("stop")),
                stopAbv = getString("stopAbv"),
                message = getString("message"),
                directionList = nullableDirectionList(getJSONArray("direction"))
            )
        }
    }

    fun nullableDirectionList(jsonArray: JSONArray?): List<Direction> {
        return jsonArray?.let { array ->
            (0 until array.length()).map {
                array.optJSONObject(it).run {
                    Direction(
                        name = Direction.Name.fromJson(getString("name")),
                        tramList = nullableTramList(getJSONArray("tram"))
                    )
                }
            }
        } ?: emptyList()
    }

    fun nullableTramList(jsonArray: JSONArray?): List<Tram> {
        return jsonArray?.let { array ->
            (0 until array.length()).map {
                array.optJSONObject(it).run {
                    Tram(
                        dueMins = optInt("dueMins"),
                        destination = getString("destination")
                    )
                }
            }
        } ?: emptyList()
    }
}
