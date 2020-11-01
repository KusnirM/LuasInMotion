package com.example.luasinmotionandroid.data.repository

import com.example.luasinmotionandroid.BuildConfig
import com.example.luasinmotionandroid.data.mapper.GreenLineResultDataToDomainMapper
import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.data.utlis.addQueryParams
import com.example.luasinmotionandroid.domain.model.GreenLineResult
import com.example.luasinmotionandroid.domain.repository.LuanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class LuanRepositoryImpl(
    private val greenLineResultDataToDomainMapper: GreenLineResultDataToDomainMapper,
    private val okHttpClient: OkHttpClient
) : LuanRepository {

    override suspend fun getGreenLine(stop: Stop): GreenLineResult {

        val params = mapOf(
            QueryParam.ACTION to "forecast",
            QueryParam.STOP to stop.abv,
            QueryParam.ENCRYPT to "${!BuildConfig.DEBUG}"
            /**
             *  I am not sure, but this may be for product use?
             *  If so this would be the way to do so,
             *  and we would need to add decrypting method to response mapper
             */
        )

        val url = "${BuildConfig.BASE_URL}${Endpoint.STOP_INFO}"
        val httpBuilder = HttpUrl
            .parse(url)
            ?.newBuilder()
            ?.addQueryParams(params)
            ?.build()

        val request = Request.Builder()
            .url(httpBuilder)//todo handle NPE
            .build()

        return withContext(Dispatchers.IO) {
            try {
                val response = okHttpClient.newCall(request).execute()
                greenLineResultDataToDomainMapper.map(response)
            } catch (e: Exception) {
                greenLineResultDataToDomainMapper.map(e)
            }
        }
    }

    object Endpoint {
        const val STOP_INFO = "/xml/get.ashx"
    }

    object QueryParam {
        const val ACTION = "action"
        const val STOP = "stop"
        const val ENCRYPT = "encrypt"
    }
}
