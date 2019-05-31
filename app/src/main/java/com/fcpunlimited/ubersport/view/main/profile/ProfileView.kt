package com.fcpunlimited.ubersport.view.main.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.fcpunlimited.ubersport.view.IBaseView

interface ProfileView : MvpView, IBaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showActiveGames()
}