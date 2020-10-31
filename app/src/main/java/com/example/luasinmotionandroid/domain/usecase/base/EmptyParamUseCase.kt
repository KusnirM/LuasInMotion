package com.example.luasinmotionandroid.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class EmptyParamUseCase<out Type> where Type : Any? {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    abstract suspend fun run(): Result<Type, Exception>

    open operator fun invoke(
        scope: CoroutineScope,
        onResult: (Result<Type, Exception>) -> Unit = {}
    ) {
        scope.launch {
            val job = withContext(ioDispatcher) {
                run()
            }
            withContext(scope.coroutineContext) {
                onResult(job)
            }
        }
    }
}
