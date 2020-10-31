package com.example.luasinmotionandroid.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out Type, in Params> where Type : Any? {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    abstract suspend fun run(params: Params): Result<Type, Exception>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Result<Type, Exception>) -> Unit = {}
    ) {
        scope.launch {
            val job = withContext(ioDispatcher) {
                run(params)
            }
            withContext(scope.coroutineContext) {
                onResult(job)
            }
        }
    }
}
