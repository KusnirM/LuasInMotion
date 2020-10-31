package com.example.luasinmotionandroid.domain.usecase.greenLine

import com.example.luasinmotionandroid.domain.model.DatePart
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.Tram
import com.example.luasinmotionandroid.domain.repository.LuanRepository
import com.example.luasinmotionandroid.domain.usecase.base.Result
import com.example.luasinmotionandroid.domain.usecase.base.UseCase
import com.example.luasinmotionandroid.exceptions.EmptyResultBodyException
import com.example.luasinmotionandroid.presentation.model.GreenLine
import org.joda.time.DateTime
import timber.log.Timber

class GetGreenLineUseCase(
    private val loanRepository: LuanRepository
) : UseCase<GreenLine, DateTime>() {

    override suspend fun run(params: DateTime): Result<GreenLine, Exception> {
        return try {
            val datePart = getDatePart(params)
            val result = loanRepository.getGreenLine(datePart.stop)

            if (result.errorResponse == null) {
                result.stopInfo?.let {
                    val line = GreenLine(
                        message = it.message,
                        datePart = datePart,
                        tramList = mapTramList(datePart.direction, it.directionList)
                    )
                    Result.Success(line)
                } ?: throw EmptyResultBodyException()
            } else {
                throw Exception(result.errorResponse.toString())
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.Failure(e)
        }
    }

    fun getDatePart(time: DateTime): DatePart {
        return if (time.hourOfDay > 12) DatePart.EVENING
        else DatePart.MORNING
    }

    fun mapTramList(name: Direction.Name, list: List<Direction>): List<Tram> {
        return list.firstOrNull { it.name == name }?.tramList ?: emptyList()
    }
}
