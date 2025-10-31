package com.plcoding.cryptotracker.core.data

import com.plcoding.cryptotracker.core.domain.NetworkError
import com.plcoding.cryptotracker.core.domain.Result
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

/**
 * Top level function to make the request.
 * @return T the response of the request with NetworkError if something goes wrong
 * @param execute a certain request returns HttpResponse
 *
 * catching a generic exception inside a suspend function, it can backfire
 * because when a coroutine is cancelled, it throws a cancellation exception
 * and the parent coroutine will not be notified.
 * currentCoroutineContext().ensureActive() -> if there's a cancellation, the parent
 * is notified about the cancellation.
 *
 * These catch blocks errors before even getting the response from the server, and the
 * responseToResult will check for errors that happen after the response from the server.
 * */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UNKNOWN_ERROR)
    }

    return responseToResult(response = response)
}
