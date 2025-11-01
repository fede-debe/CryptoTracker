package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinDto
import com.plcoding.cryptotracker.crypto.domain.Coin

/**
 * Extension function to convert CoinDto to Coin.
 *  This is the place to take any necessary data manipulation.
 *  Data that are good to serialize but not easy to work with,
 *  and map these into data types that are hard to serialize
 *  but easy to work with (date and time for example)
 *
 *  @return Coin.
 */
fun CoinDto.toCoin() : Coin {
    return Coin(
        id = id,
        name = name,
        rank = rank,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}