package com.plcoding.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

/** Data Transfer Object which is a Kotlin representation
 * of the JSON structure. Object transferred over the network.
 *
 * Serializable is necessary for all data classes we want to
 * directly parse from JSON into a Kotlin data class.
 *
 * Coin domain has the same values, but we create the DTO object
 * but it's a violation of architecture. We could have date and
 * time (LocalDateTime it's not easy to serialize, so it would be
 * part of domain object, not the dto object).
 *
 * How we parse JSON is implementation detail and can't be done
 * with a domain object.
 *
 * DTOs are allowed to have implementation details (like serialization).
 * Params has to match the json response naming, else we need additional
 * annotations.
 * */
@Serializable
data class CoinDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)