package com.plcoding.cryptotracker.crypto.domain

data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    // better to have type of unit, in this case is the currency.
    // If not specified, it can be anything
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
)