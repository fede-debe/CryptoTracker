package com.plcoding.cryptotracker.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Implementation HTTP client to make network calls.
 *
 * 1) Logging feature -> All requests are logged (easier debugging)
 * 2) ContentNegotiation feature -> Ktor can automatically parse a
 *    Json to a Kotlin data class without doing it manually. We want
 *    to use KotlinX serialization which we configure with the json
 *    function (passing custom json parser reference) -
 *    ignoreUnknownKeys = true make sure that if there're some unknown
 *    fields of this JSON response that we don't explicitly declare in
 *    our data class we will simply ignore those.
 * 3) Configure defaultRequest -> set the content type to application/json,
 *    every request will have this content type (JSON) proper headers are
 *    attached to every request.
 * */
object HttpClientFactory {
    fun create(engine: HttpClientEngine) : HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}