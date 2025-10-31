package com.plcoding.cryptotracker.core.data

import com.plcoding.cryptotracker.BuildConfig

/** Top Level Function to help flatten to valid url.
 * 1) if url.contains(BuildConfig.BASE_URL) is true, we a passing already the full url and we just return that url.
 * 2) if we pass some kind of relative url (example: /assets), we need to add the base url and drop a slash since it's included into the Base url
 * 3) same as condition 2, without dropping the slash
 * */
fun constructUrl(url: String) : String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}