package com.example.luasinmotionandroid.data.network

import com.example.luasinmotionandroid.domain.model.StopInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LuasApi {

    @GET("/xml/get.ashx")
    suspend fun getGreenLine(
        @Query(ACTION) action: String = "forecast",
        @Query(STOP) stop: String,
        @Query(ENCRYPT) encrypt: Boolean = false
    ): Response<StopInfo>

    companion object {

        /**QUERY arguments */
        const val ACTION = "action"
        const val STOP = "stop"
        const val ENCRYPT = "encrypt"
    }
}
