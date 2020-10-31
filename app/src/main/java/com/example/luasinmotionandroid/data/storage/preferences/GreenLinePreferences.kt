package com.example.luasinmotionandroid.data.storage.preferences

import android.content.SharedPreferences
import com.example.luasinmotionandroid.data.storage.preferences.base.BasePreferences
import com.google.gson.Gson

class GreenLinePreferences(
    prefs: SharedPreferences,
    gson: Gson
) : BasePreferences(prefs, gson) {

    companion object {
        const val FILE_NAME = "greenLine"
    }
}
