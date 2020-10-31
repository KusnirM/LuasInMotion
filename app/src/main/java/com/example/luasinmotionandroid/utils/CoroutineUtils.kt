package com.example.luasinmotionandroid.utils

import com.example.luasinmotionandroid.domain.usecase.base.EmptyParamUseCase
import com.example.luasinmotionandroid.domain.usecase.base.UseCase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * helper methods when you need just simple blocking return value from useCase
 */

fun <Type, Params> UseCase<Type, Params>.await(params: Params, isOnWorkerThread: Boolean = true, default: Type? = null): Type? where Type : Any? {
    val dispatcher = if (isOnWorkerThread) Dispatchers.IO else Dispatchers.Main
    return runBlocking(dispatcher) {

        val returnValue = CompletableDeferred<Type?>()
        invoke(this, params) { result ->
            result.result(
                onSuccess = { returnValue.complete(it) },
                onFailure = { returnValue.complete(default) }
            )
        }

        returnValue.await()
    }
}

fun <Type> EmptyParamUseCase<Type>.await(isOnWorkerThread: Boolean = true, default: Type? = null): Type? where Type : Any? {
    val dispatcher = if (isOnWorkerThread) Dispatchers.IO else Dispatchers.Main
    return runBlocking(dispatcher) {

        val returnValue = CompletableDeferred<Type?>()
        invoke(this) { result ->
            result.result(
                onSuccess = { returnValue.complete(it) },
                onFailure = { returnValue.complete(default) }
            )
        }
        returnValue.await()
    }
}
