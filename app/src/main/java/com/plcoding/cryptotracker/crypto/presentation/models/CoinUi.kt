package com.plcoding.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.plcoding.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.plcoding.cryptotracker.crypto.domain.Coin
import java.text.NumberFormat
import java.util.Locale

/**
 * Domain model require a completely different representation for the UI.
 * Ui only has to display values, it shouldn't need to do any processing or
 * calculations on its own (easier to test).
 * We need a different UI model from the Coin domain
 * */
data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val absoluteChange24Hr: DisplayableNumber,
    val hasPositiveTrend: Boolean,
    @DrawableRes val iconRes: Int
)

/** Display formatted number without UI knowledge.
 * @param value to be formatted and still make calculations
 * @param formatted string to display within the UI
 * */
data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUi() = CoinUi(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    priceUsd = priceUsd.toDisplayableNumber(),
    marketCapUsd = marketCapUsd.toDisplayableNumber(),
    changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(symbol = symbol),
    absoluteChange24Hr = (priceUsd * (changePercent24Hr / 100)).toDisplayableNumber(),
    hasPositiveTrend = changePercent24Hr > 0.0
)

/** All the value we format will have exactly 2 decimal places */
fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}