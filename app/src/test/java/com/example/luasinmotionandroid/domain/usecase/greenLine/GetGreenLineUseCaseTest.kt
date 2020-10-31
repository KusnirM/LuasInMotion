package com.example.luasinmotionandroid.domain.usecase.greenLine

import com.example.luasinmotionandroid.domain.model.DatePart
import com.example.luasinmotionandroid.domain.model.Direction
import com.example.luasinmotionandroid.domain.model.GreenLineResult
import com.example.luasinmotionandroid.domain.repository.LuanRepository
import com.example.luasinmotionandroid.domain.usecase.base.Result
import com.example.luasinmotionandroid.presentation.model.GreenLine
import com.example.luasinmotionandroid.stubbs.StubbsStopInfo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetGreenLineUseCaseTest {

    private lateinit var useCase: GetGreenLineUseCase
    private val luanRepository = mock<LuanRepository>()

    @Before
    fun setup() {
        useCase = GetGreenLineUseCase(luanRepository)
    }

    @Test
    fun `will see`() {
        runBlocking {
            val morningHour = 5
            val actualTime = DateTime(2020, 1, 1, morningHour, 1)
            val morningDatePart = useCase.getDatePart(actualTime)
            val actualStopInfo = StubbsStopInfo.getStopInfo(
                directionList = listOf(
                    StubbsStopInfo.direction(name = morningDatePart.direction),
                    StubbsStopInfo.direction(name = Direction.Name.OUTBOUND)
                )
            )
            whenever(luanRepository.getGreenLine(any())) doReturn GreenLineResult(actualStopInfo)

            val expected = GreenLine(
                message = actualStopInfo.message,
                datePart = morningDatePart,
                tramList = listOf(StubbsStopInfo.tram())
            )

            useCase.invoke(this, actualTime) {
                assert(it is Result.Success)
                it.result(
                    onSuccess = {
                        val actual = it
                        assertEquals(expected, actual)
                    }
                )
            }
        }
    }

    @Test
    fun `when current time is between in the morning should map to the morning`() {
        val hourOfDay = 4
        val time = DateTime(2020, 1, 1, hourOfDay, 3, 3, 3)
        val actual = useCase.getDatePart(time)
        val expected = DatePart.MORNING
        assertEquals(expected, actual)
    }

    @Test
    fun `when current time is between in the morning should map to the evening`() {
        val hourOfDay = 14
        val time = DateTime(2020, 1, 1, hourOfDay, 3, 3, 3)
        val actual = useCase.getDatePart(time)
        val expected = DatePart.EVENING
        assertEquals(expected, actual)
    }

    @Test
    fun `should return first item of directions name`() {
        val expectedName = Direction.Name.OUTBOUND
        val directionList = listOf(
            StubbsStopInfo.direction(name = Direction.Name.INBOUND),
            StubbsStopInfo.direction(name = Direction.Name.OUTBOUND)
        )

        val expected = listOf(StubbsStopInfo.tram())
        val actual = useCase.mapTramList(expectedName, directionList)

        assertEquals(expected, actual)
    }
}
