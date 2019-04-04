package com.fcpunlimited.ubersport.view.main.search.dialog

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface SportsFilterDialogView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)
}