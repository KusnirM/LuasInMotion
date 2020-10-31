package com.example.luasinmotionandroid.data.repository

import com.example.luasinmotionandroid.data.mapper.GreenLineResultDataToDomainMapper
import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.data.network.LuasApi
import com.example.luasinmotionandroid.data.storage.preferences.GreenLinePreferences
import com.example.luasinmotionandroid.domain.model.GreenLineResult
import com.example.luasinmotionandroid.domain.repository.LuanRepository

// posibly later divide into 2 implmementations: local and remote
class LuanRepositoryImpl(
    private val greenLineResultDataToDomainMapper: GreenLineResultDataToDomainMapper,
    private val luasApi: LuasApi,
    private val prefs: GreenLinePreferences
) : LuanRepository {
    override suspend fun getGreenLine(stop: Stop): GreenLineResult {
//        return greenLineResultDataToDomainMapper.map(
//            luasApi.getGreenLine(
//                etag = getGreenLineEtag()?:"",
//                stop = stop.serializedName
//            )
//        )

        return greenLineResultDataToDomainMapper.map(mockedResponse(stop))
    }

    override suspend fun setGreenLineEtag(etag: String) {
        prefs.insert(KEY_ETAG, etag)
    }

    override suspend fun getGreenLineEtag(): String? {
        return prefs.getNullableString(KEY_ETAG)
    }

    override suspend fun clearEtag() {
        prefs.delete(KEY_ETAG)
    }

    companion object {
        const val KEY_ETAG = "LuanRepositoryImpl.etag"
    }

    // todo move to test
    fun mockedResponse(stop: Stop): String {
        return when (stop) {
            Stop.STILLORGAN -> stubbedStillorgan
            Stop.MARLBOROUGH -> stubbedMarlborough
            else -> ""
        }
    }

    val stubbedMarlborough =
        """
           <stopInfo created="2020-10-31T12:55:50" stop="Marlborough" stopAbv="MAR">
	<message>Green Line services operating normally</message>
	<direction name="Inbound">
		<tram destination="No Northbound Service" dueMins="" />
	</direction>
	<direction name="Outbound">
		<tram dueMins="2" destination="Bride's Glen" />
		<tram dueMins="8" destination="Bride's Glen" />
		<tram dueMins="17" destination="Bride's Glen" />
	</direction>
</stopInfo>
        """.trimIndent()

    val stubbedStillorgan =
        """
        <stopInfo created="2020-10-31T12:53:46" stop="Stillorgan" stopAbv="STI">
        	<message>Green Line services operating normally</message>
        	<direction name="Inbound">
        		<tram dueMins="2" destination="Broombridge" />
        		<tram dueMins="7" destination="Parnell" />
        		<tram dueMins="17" destination="Broombridge" />
        	</direction>
        	<direction name="Outbound">
        		<tram dueMins="1" destination="Bride's Glen" />
        		<tram dueMins="9" destination="Sandyford" />
        		<tram dueMins="16" destination="Bride's Glen" />
        	</direction>
        </stopInfo>
    """.trimIndent()
}
