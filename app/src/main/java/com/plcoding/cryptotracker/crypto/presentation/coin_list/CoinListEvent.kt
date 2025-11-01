package com.plcoding.cryptotracker.crypto.presentation.coin_list

import com.plcoding.cryptotracker.core.domain.NetworkError

/**
 * Events: One time information that we send from the ViewModel
 * to the UI. [CoinListAction] are from UI to ViewModel.
 *
 * In this example when an error happens, we want to pass an error
 * message within the UI.
 * If we want to localize the app (translate to different languages
 * using string resources), instead of passing the actual string, we
 * can pass the actual error that happened [NetworkError]. We send the
 * specific error type to the UI, which we then map it to the
 * corresponding string resource.
 * */
sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}