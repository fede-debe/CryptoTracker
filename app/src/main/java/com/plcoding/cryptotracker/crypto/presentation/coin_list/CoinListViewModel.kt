package com.plcoding.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.onError
import com.plcoding.cryptotracker.core.domain.onSuccess
import com.plcoding.cryptotracker.crypto.domain.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * VM it is kind of the logic behind the UI.
 * It talks to data sources, and its responsibility is to
 * keep our UI state. The VM will hold the reference to the
 * uiState (or keep existing data), it is responsible to
 * update the state so the UI can observe and reflect the changes.
 *
 * It holds data during configuration changes:
 * - device rotation
 * - system language
 * - device theme
 * - etc
 *
 * The activity is destroyed and recreated, the Vm provides a way
 * to store the uiState (data) outside of that activity. Otherwise
 * the state would be destroyed together with the activity. VM will
 * outlive the lifecycle of an activity.
 *
 * @param coinDataSource: We pass the interface otherwise we would
 * violate our architecture. Every layer is allowed to access
 * the domain layer, in this case is the CoinDataSource.
 * Also we should always depend on abstractions, not
 * concrete implementation. This is because we can't pass a different
 * instance to the concrete implementation while testing the VM.
 * By passing the abstraction, we can pass any type of CoinDataSource,
 * if we use the implementation directly, we are forced to use it.
 *
 * An init block to execute loadCoins() (load initial data) has a
 * side effect when we want to test the VM. We can't control when
 * the init block fires, since it fires when we create the VM.
 *
 * We are loading initial data when the flow collection of our state
 * actually starts --> .onStart is executed when the composed UI starts
 * subscribing to updates of the state. This way we control over when
 * we trigger it.
 * .stateIn to make it as StateFlow which really catches the result.
 * SharingStarted.WhileSubscribed(5000L) makes sure to not refetch data
 * while configuration changes, only if the collector disappear for more
 * than 5 seconds.
 * */
class CoinListViewModel(private val coinDataSource: CoinDataSource) : ViewModel() {

    /** Only the VM should be able to update the state.
     * If there's a bug related to update the state,
     * the VM is the only place to look at.
     */
    private val _uiState = MutableStateFlow(CoinListState())
    val state = _uiState.onStart {
        loadCoins()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    fun onActions(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {}
            CoinListAction.OnRefresh -> { loadCoins() }
        }
    }


    /** coroutines scope is need to execute a suspend function */
    private fun loadCoins() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            coins = coins.map { coin -> coin.toCoinUi() })
                    }
                }
                .onError { error ->
                    _uiState.update { state -> state.copy(isLoading = false) }
                }
        }
    }
}

