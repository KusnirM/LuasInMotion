package com.example.luasinmotionandroid.data.mapper

import com.example.luasinmotionandroid.data.mapper.base.BaseResponseMapper
import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.data.utlis.getDateTime
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.GreenLineResult
import com.example.luasinmotionandroid.domain.model.StopInfo
import com.example.luasinmotionandroid.domain.model.Tram
import com.example.luasinmotionandroid.exceptions.EmptyResultBodyException
import com.example.luasinmotionandroid.exceptions.IllegalParamException
import com.example.luasinmotionandroid.utils.isAnInt
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale

class GreenLineResultDataToDomainMapper : BaseResponseMapper() {

    fun map(response: Response): GreenLineResult {
        return when {
            response.isSuccessful -> {
                response.body()?.string()?.let { body ->

                    if (shouldThrowIllegalParamException(body)) {
                        throw IllegalParamException(body)
                    }

                    // xml converter converts 1 tag in list into object, adding test to handle either array or object
                    val json = XmlToJson.Builder(body).build()
                        .toJson() // todo if whole api is based on xml move this to base mapping

                    val obj = json?.optJSONObject("stopInfo")
                    getStopInfo(obj)?.let {
                        GreenLineResult(stopInfo = it)
                    }
                } ?: GreenLineResult(errorResponse = mapException(EmptyResultBodyException()))
            }
            else -> GreenLineResult(errorResponse = super.mapUnsuccessful(response))
        }
    }

    // these response bodies should not really be 200 error code, but as it is the case,
    // we should threat them as exceptions
    fun shouldThrowIllegalParamException(json: String): Boolean {
        return json.toLowerCase(Locale.getDefault()).startsWith("exception")
    }

    fun map(e: Exception): GreenLineResult {
        return GreenLineResult(errorResponse = mapException(e))
    }

    fun getStopInfo(json: JSONObject?): StopInfo? {
        return json?.run {
            StopInfo(
                created = getDateTime("created"),
                stop = Stop.fromJson(getString("stop")),
                message = getString("message"),
                directionList = nullableDirectionList(this)
            )
        }
    }

    private fun nullableDirectionList(json: JSONObject): List<Direction> {
        val key = "direction"
        return try {
            val array = json.getJSONArray(key)
            (0 until array.length()).map {
                array.optJSONObject(it).run {
                    getDirection(this)
                }
            }
        } catch (ex: JSONException) {
            val directionObj = json.getJSONObject(key) ?: return emptyList()
            listOf(getDirection(directionObj))
        } catch (ex: Exception) {
            emptyList()
        }
    }

    private fun getDirection(jsonObj: JSONObject): Direction {
        return Direction(
            name = Direction.Name.fromJson(jsonObj.getString("name")),
            tramList = nullableTramList(jsonObj)
        )
    }

    /**
     * due mins can return an empty Value, hence int is not a best choice
     * i rather let is as string, i believe there should be better option from api than that
     * but as it is the case I rather added handler if server would send us some "random number or text"
     * so we would not crash over there, i am testing if String.isAnInt, if not i also keep it as empty string
     * would be great candidate to consult with server guys 1st why is it like that before coming to product team
     */

    private fun nullableTramList(json: JSONObject): List<Tram> {
        val key = "tram"
        return try {
            val array = json.getJSONArray(key)
            (0 until array.length()).map {
                getTram(array.optJSONObject(it))
            }
        } catch (ex: JSONException) {
            val stringJson = json.optJSONObject(key) ?: return emptyList()
            listOf(getTram(stringJson))
        } catch (ex: Exception) {
            emptyList()
        }
    }

    private fun getTram(json: JSONObject): Tram {
        return Tram(
            dueMins = handleDueMins(json.getString("dueMins")),
            destination = json.getString("destination")
        )
    }

    fun handleDueMins(dueMins: String): String {
        return if (dueMins.isAnInt()) dueMins else ""
    }
}
