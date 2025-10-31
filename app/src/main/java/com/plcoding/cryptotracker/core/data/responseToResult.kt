package com.plcoding.cryptotracker.core.data

import com.plcoding.cryptotracker.core.domain.NetworkError
import com.plcoding.cryptotracker.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/** Top level function to take a specific HTTP status code,
 * and map that error to the corresponding result type
 *
 * T -> type of success dara from endpoint
 * NetworkError -> type of specific error data from endpoint
 *
 * Depending on status code, we map into corresponding result class.
 * Starting with 2, successful request - take return json body and
 * convert it to the requested data type (example: Coin).
 *
 * suspend since the .body function is a suspend function,
 * it can take a moment to parse the result.
 *
 * inline keyword is needed together with reified, and reified is
 * needed to get the type information on the generic argument. The
 * body function wouldn't know which specific type we want to parse
 * into (generic type info is only available at compile time, not
 * during runtime)
 * */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN_ERROR)
    }
}