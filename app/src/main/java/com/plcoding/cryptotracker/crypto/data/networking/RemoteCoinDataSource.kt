package com.plcoding.cryptotracker.crypto.data.networking

import com.plcoding.cryptotracker.core.data.constructUrl
import com.plcoding.cryptotracker.core.data.safeCall
import com.plcoding.cryptotracker.core.domain.NetworkError
import com.plcoding.cryptotracker.core.domain.Result
import com.plcoding.cryptotracker.core.domain.map
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

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
}