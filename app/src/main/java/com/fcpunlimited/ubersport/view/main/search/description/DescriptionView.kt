package com.fcpunlimited.ubersport.view.main.search.description

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface DescriptionView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun joinedToGame()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun leavedGame()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}