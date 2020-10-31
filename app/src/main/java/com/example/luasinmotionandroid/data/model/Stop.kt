package com.example.luasinmotionandroid.data.model

import androidx.annotation.StringRes
import com.example.luasinmotionandroid.R
import timber.log.Timber

// depending of if we expect to update this list dynamically in api we would use either enum or simply
// would be mapping list
enum class Stop(val serializedName: String, @StringRes val displayName: Int) {
    MARLBOROUGH("mar", R.string.stop_marlborough),
    STILLORGAN("sti", R.string.stop_stillorgan),
    UNKNOWN("", -1);

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
