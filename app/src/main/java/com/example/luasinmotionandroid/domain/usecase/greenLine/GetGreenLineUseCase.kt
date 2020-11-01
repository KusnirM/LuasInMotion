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
import java.net.SocketTimeoutException

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

                // currently not sure if same error handling would apply on other API endpoint, if so we could extract handling into default usecase
                // otherwise we can decide here
                throw result.errorResponse
            }
        } catch (e: Exception) {
            /**
             * exception should be divided into expected, unexpected
             * expected: discuss with client what should be message for different type of exceptions:
             * HttpExceptions:  easily by error code: but seems like some issues ends up in code 200 anyway :)
             * SocketTimeoutException -> friendly display
             *
             * i extracted IllegalParamException as result of wrong api ERROR handling: we get 200 with
             * body starting as :  Exception:   this is high likely due to API update, or by issue in the app(should not happen as i added tests: ))
             *
             * Unexpected should just use general error, and Timber.e so we could find out easily what happened
             */

            when (e) {
                is SocketTimeoutException -> Unit // we can exclude some exception from log.
                else -> {
                    Timber.e(e)
                }
            }
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
