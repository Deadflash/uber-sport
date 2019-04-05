package com.fcpunlimited.ubersport.view.main.search

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.fcpunlimited.ubersport.view.IBaseView

interface SearchView : MvpView, IBaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onSwipeRefresh(isRefreshing: Boolean)
}