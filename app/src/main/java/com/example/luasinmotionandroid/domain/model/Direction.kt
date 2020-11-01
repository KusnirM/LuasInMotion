package com.example.luasinmotionandroid.domain.model

import androidx.annotation.StringRes
import com.example.luasinmotionandroid.R
import timber.log.Timber

data class Direction(
    val name: Name,
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
