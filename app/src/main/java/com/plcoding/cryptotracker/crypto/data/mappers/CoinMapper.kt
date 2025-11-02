package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.plcoding.cryptotracker.crypto.domain.Coin
import com.plcoding.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

/**
 * Extension function to convert CoinDto to Coin.
 *  This is the place to take any necessary data manipulation.
 *  Data that are good to serialize but not easy to work with,
 *  and map these into data types that are hard to serialize
 *  but easy to work with (date and time for example)
 *
 *  @return Coin.
 */
fun CoinDto.toCoin(): Coin {
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

/** CoinPriceDto holds a reference to the Long timestamp which is not
 * easy to work with. We need to convert it to a simpler type to work with.
 * This is why we convert it to a ZoneDateTime type. You can easily subtract
 * days,milliseconds,hours,etc.. whatever you want to do in regards to date
 * math.
 * */
fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC"))
    )
}