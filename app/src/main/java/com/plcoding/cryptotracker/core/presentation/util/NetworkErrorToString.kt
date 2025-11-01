package com.plcoding.cryptotracker.core.presentation.util

import android.content.Context
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.domain.NetworkError

/**
 * Top level function to map a [NetworkError] to a string resource
 * to provide a localized app with different languages.
 * */
fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknow
        NetworkError.UNKNOWN_ERROR -> R.string.error_unknow
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_request
    }
    return context.getString(resId)
}