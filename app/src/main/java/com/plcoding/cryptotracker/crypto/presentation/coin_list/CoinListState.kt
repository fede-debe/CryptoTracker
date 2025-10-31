package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi

/** Immutable to let compiler know this class never change
 * compose don't know if the list (considered not stable by compose)
 * changed or not and could recompose more often than necessary.
 * With Immutable, it only changes when the instance changes */
@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)