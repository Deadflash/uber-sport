package com.fcpunlimited.ubersport.view.main.search

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView : MvpView {
    fun onSwipeRefresh(isRefreshing: Boolean)
}