package com.example.luasinmotionandroid.domain.usecase.base

class TestBaseUseCase(
    private val testRepository: TestRepository
) : UseCase<Unit, Unit>() {
    override suspend fun run(params: Unit): Result<Unit, Exception> {
        return try {
            testRepository.function()
            Result.Success(Unit)
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }
}
