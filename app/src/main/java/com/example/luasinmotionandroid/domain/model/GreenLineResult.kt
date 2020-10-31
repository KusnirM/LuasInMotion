package com.example.luasinmotionandroid.domain.model

import androidx.annotation.StringRes
import com.example.luasinmotionandroid.R
import com.example.luasinmotionandroid.data.model.Stop
import org.joda.time.DateTime
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import timber.log.Timber

data class GreenLineResult(
    val stopInfo: StopInfo? = null,
    val errorResponse: ErrorResponse? = null
)

@Root(name = "stopInfo", strict = false)
data class StopInfo(
    @field: Element(name = "created")
    val created: DateTime,
    @field: Element(name = "stop")
    val stop: Stop,
    @field: Element(name = "stopAbv")
    val stopAbv: String,
    @field: Element(name = "message")
    val message: String = "",
    @field: ElementList(name = "direction")
    val directionList: List<Direction> = emptyList()
)

@Root(name = "direction", strict = false)
data class Direction(
    @field: Element(name = "name")
    val name: Name,
    @field: ElementList(name = "tram")
    val tramList: List<Tram> = emptyList()
) {

    enum class Name(val serializedName: String, @StringRes val displayName: Int) {
        INBOUND("Inbound", R.string.direction_name_inbound_display_name),
        OUTBOUND("Outbound", R.string.direction_name_outbound_display_name),
        UNKNOWN("", -1);

        companion object {
            fun fromJson(json: String): Name {
                return values().find {
                    it.serializedName == json
                } ?: run {
                    Timber.e("Unknown type: $json")
                    UNKNOWN
                }
            }
        }
    }
}

@Root(name = "tram", strict = false)
data class Tram @JvmOverloads constructor(
    @field: Element(name = "dueMins", required = false)
    val dueMins: Int = -1,
    @field: Element(name = "destination", required = false)
    val destination: String = ""
)
