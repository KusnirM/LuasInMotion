package com.example.luasinmotionandroid.domain.usecase.base

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.NullPointerException

class UseCaseTest {

    private lateinit var testBaseUseCase: TestBaseUseCase
    private val testRepository = mock<TestRepository>()

    @Before
    fun setUp() {
        testBaseUseCase = TestBaseUseCase(testRepository)
    }

    @Test
    fun `should handle onSuccess`() {
        runBlocking {
            testBaseUseCase.invoke(this, Unit) {
                assert(it is Result.Success)
            }
        }
    }

    @Test
    fun `should handle onFailure and show correct message`() {
        val msg = "msg"
        val ex = NullPointerException(msg)
        whenever(testRepository.function()) doThrow ex
        runBlocking {
            testBaseUseCase.invoke(this, Unit) {
                assert(it is Result.Failure)
                it.result(
                    onFailure = {
                        assert(it is NullPointerException)
                        assertEquals(it.message, msg)
                    }
                )
            }
        }
    }
}
