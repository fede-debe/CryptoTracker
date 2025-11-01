package com.plcoding.cryptotracker.di

import com.plcoding.cryptotracker.core.data.HttpClientFactory
import com.plcoding.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin DI -> Improve testability across the project,
 * we can replace instances (like an instance of CoinDataSource
 * that is optimized for testing, a Test Double. This is only
 * work if we inject those dependencies.
 *
 * It is simply passing instances of some kind of classes or functions
 * via constructor or a function parameter. We could pass everything
 * manually.
 * */

/**
 * It bundles related dependencies that are coupled to a specific lifetime
 * of our application into a single module. Singletons.
 * In the module we provide those dependencies we need to inject.
 *
 * @see HttpClientFactory: different engines to use, we use CIO (engine for
 * production code) here but we could also use a mock engine to mocks
 * network calls and get fake responses to test how the app handles
 * those without making actual network calls.
 *
 * @see RemoteCoinDataSource we bind the instance to the CoinDataSource,
 * every time we request a CoinDataSource instance, the RemoteCoinDataSource
 * implementation will be provided.
 *
 * @see CoinListViewModel: viewModelOf() is needed since viewModels need to
 * be created a bit differently compared to regular classes. It requires a
 * factory under the hood.
 *
 * */

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    // this way we don't need to add more get() functions to inject more instances
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}
