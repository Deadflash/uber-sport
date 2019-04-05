package com.fcpunlimited.ubersport.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface IBaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}