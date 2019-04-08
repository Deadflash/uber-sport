package com.fcpunlimited.ubersport.view.splash

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.fcpunlimited.ubersport.view.IBaseView

interface SplashView : MvpView, IBaseView {

    @StateStrategyType(SkipStrategy::class)
    fun setAuthorized(isAuthorized: Boolean)
}