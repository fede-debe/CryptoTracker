package com.plcoding.cryptotracker.crypto.data.networking

import com.plcoding.cryptotracker.core.data.constructUrl
import com.plcoding.cryptotracker.core.data.safeCall
import com.plcoding.cryptotracker.core.domain.NetworkError
import com.plcoding.cryptotracker.core.domain.Result
import com.plcoding.cryptotracker.core.domain.map
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.data.mappers.toCoinPrice
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Load coins from remote data source.
 * @param httpClient ktor http client to fetch coins
 *
 * */
class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        // return expected result data or result NetworkError
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { responseDto ->
            // each object from responseDto.data is mapped to Coin object
            responseDto.data.map { coinDto -> coinDto.toCoin() }
        }
    }

    /**
     * Get coin history from remote data source.
     * @param coinId coin id
     * @param start start time milliseconds
     * @param end end time milliseconds
     *
     * @return expected result data or result NetworkError
     *
     * Interval is always h6 - get price changes in chunks
     * of 6 hours (if price changed last 6 hours) We could
     * pass it as parameter and let the user decide which
     * value to provide.
     **/
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { historyDto ->
            historyDto.data.map { coinPriceDto -> coinPriceDto.toCoinPrice() }
        }
    }
}