package com.plcoding.cryptotracker.crypto.domain

import com.plcoding.cryptotracker.core.domain.NetworkError
import com.plcoding.cryptotracker.core.domain.Result

/** Defining what a CoinDataSource consist of, what kind of
 * data we expect it to return, not how returns it (implementation
 * detail belong in the data layer).
 *
 * If success call we get List<Coin> else NetworkError.
 * The implementation of this data source will be in the data layer.
 * Domain is the innermost layer, both presentation and data are allowed
 * to access classes from domain, but not vice versa.
 * */
interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}