package com.plcoding.cryptotracker.crypto.presentation.coin_list

import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi

/**
 * MVI architectural pattern. We work with actions -> Model View Intent
 *  The Intent bundles all the different user interactions, what user can
 *  possibly do on the screen (click on item, refresh data, select a checkbox,
 *  etc..).
 *
 *  Whatever action the user can perform within the app, that we need to
 *  react within the ViewModel (which correspond to a state update), these
 *  actions are bundle into an Action Class, which is a sealed (limited
 *  number of options) interface.
 *  This class bundles these different actions for a single screen.
 *  */
sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
    data object OnRefresh: CoinListAction // swipe to refresh action
}