package com.example.luasinmotionandroid.stubbs

import com.example.luasinmotionandroid.data.model.Stop
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.StopInfo
import com.example.luasinmotionandroid.domain.model.Tram
import org.joda.time.DateTime

/**
 * todo this would be in time divided into different sections by object types
 *
 *
 */

object StubbsStopInfo {
    val created = DateTime(2020, 1, 1, 1, 1, 1)
    val stop = Stop.MARLBOROUGH
    val message = "message"
    val name: Direction.Name = Direction.Name.OUTBOUND

    val dueMins: String = "1"
    val destination: String = "destination"

    /**
     *         can add other default arguments over here, even kotlin allows to add them to constructor
     *         i highly prefer not to use them especially on larger projects. it can get easily go wrong
     *         by forgetting to assign value: of course that should be picked up by tests.
     *         Hence for test stubs this is the way i do so.
     */
    fun getStopInfo(
        created: DateTime = this.created,
        stop: Stop = this.stop,
        message: String = this.message,
        directionList: List<Direction> = listOf(direction())
    ): StopInfo {
        return StopInfo(
            created = created,
            stop = stop,
            message = message,
            directionList = directionList
        )
    }

    fun direction(
        name: Direction.Name = this.name,
        tramList: List<Tram> = listOf(tram())
    ): Direction {
        return Direction(name = name, tramList = tramList)
    }

    fun tram(
        dueMins: String = this.dueMins,
        destination: String = this.destination
    ): Tram {
        return Tram(
            dueMins = dueMins,
            destination = destination
        )
    }
}
