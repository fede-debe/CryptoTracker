package com.plcoding.cryptotracker.crypto.domain

import java.time.ZonedDateTime

/** Since it's a domain and we don't need to serialize them, we
 * can work with those types of data models that are most
 * convenient for us, like ZonedDateTime when we have to work
 * with data/time references. We could also pass a Long but whenever
 * we have to work with that, we need to convert it to something that
 * we actually can work with (unreadable conversions).
 * */
data class CoinPrice(
    val priceUsd: Double,
    val dateTime: ZonedDateTime
)