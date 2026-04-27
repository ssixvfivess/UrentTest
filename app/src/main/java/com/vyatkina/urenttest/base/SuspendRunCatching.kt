package com.vyatkina.urenttest.base

import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> suspendRunCatching(
    crossinline block: suspend () -> T,
): Result<T> {
    return try {
        Result.success(block())
    } catch (throwable: Throwable) {
        if (throwable is CancellationException) {
            throw throwable
        }
        Result.failure(throwable)
    }
}
