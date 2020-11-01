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
                response.body?.string()?.let { body ->

                    if (shouldThrowIllegalParamException(body)) {
                        throw IllegalParamException(body)
                    }

                    /**
                     * as mentioned in base response mapper i believe i could move this transformation
                     * to the base class and abstract it behind interface
                     * i did not get chance to work with xml responses recently, with more time
                     * i could check if we still can use retrofit for that
                     * but i tried SimpleXml and also JAXB and seems like there is not support
                     * hence i grabbed 1st library i found, converted into json and then to classes: )
                     */
                    val json = XmlToJson.Builder(body).build().toJson()

                    val obj = json?.optJSONObject("stopInfo")
                    getStopInfo(obj)?.let {
                        GreenLineResult(stopInfo = it)
                    }
                } ?: GreenLineResult(errorResponse = mapException(EmptyResultBodyException()))
            }
            else -> GreenLineResult(errorResponse = super.mapUnsuccessful(response))
        }
    }

    fun map(e: Exception): GreenLineResult {
        return GreenLineResult(errorResponse = mapException(e))
    }

    /**
     *  these response bodies should not really be 200 error code, but as it is the case,
     *  we should threat them as exceptions
     */
    fun shouldThrowIllegalParamException(json: String): Boolean {
        return json.toLowerCase(Locale.getDefault()).startsWith("exception")
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

    /**
     * due minutes can return an empty value, hence int is not the best choice.
     * Therefore I let is as string.
     * I believe there should be better option from api than that
     * but as it is the case I rather added handler if server would send us some "random number or text"
     * so we would not crash over there, i am testing if String.isAnInt, if not i also keep it as empty string
     * for the same reason i decided to threat negative number as empty string too.
     * would be great candidate to consult with server guys 1st why is it like that before coming to product team
     */

    fun handleDueMins(dueMins: String): String {
        /**
         * i spotted there due, tbh do not remember if it was "DUE", "due", "Due"
         * this way i keep it safe to do what is Intended
         */
        return if (dueMins.toUpperCase(Locale.ROOT) == "DUE") "Due"
        else if (dueMins.isAnInt() && dueMins.toInt() > 0) dueMins else ""
    }
}
