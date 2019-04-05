package com.fcpunlimited.ubersport.view.main.search.description

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.view.IBaseView

interface DescriptionView : MvpView, IBaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun joinedGame(game: GameFragment)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun leavedGame(game: GameFragment)
}