package com.example.luasinmotionandroid.data.model

import androidx.annotation.StringRes
import com.example.luasinmotionandroid.R
import timber.log.Timber

/**
 * I decided to declare it statically as enum for easier use through the app as I do not believe
 * serialized names currently will be changing. But if it is the case I would use different approach:
 *
 *Using a map like:
 * That would protect user even by unexpected server changes, and would be able to display results regardless
 * But it would cost some readability and typeSafety over the app
 * etc:    typealias StopsMap = Map<String, List<StopInfo>> where key -> abv
 */
enum class Stop(val serializedName: String = "", val abv: String = "", @StringRes val displayName: Int = -1) {
    MARLBOROUGH("Marlborough", "mar", R.string.stop_marlborough),
    STILLORGAN("Stillorgan", "sti", R.string.stop_stillorgan),
    UNKNOWN;

    companion object {
        fun fromJson(json: String): Stop {
            return values().find {
                it.serializedName == json
            } ?: run {
                Timber.e("Unknown type: $json")
                UNKNOWN
            }
        }
    }
}
