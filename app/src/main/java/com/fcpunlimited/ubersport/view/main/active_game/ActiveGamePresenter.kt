package com.fcpunlimited.ubersport.view.main.active_game

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.game.ActiveGamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack

@InjectViewState
class ActiveGamePresenter(private val gameModel: GameModel,
                          private val activeGamesLiveDataContainer: ActiveGamesLiveDataContainer)
    : MvpPresenter<ActiveGameView>(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateProfileView() {
        viewState.onSwipeRefresh(true)
        gameModel.getActiveGames(object : HttpEmptyResponseCallBack {
            override fun onResponse() {
                viewState.onSwipeRefresh(false)
            }

            override fun onFailure(message: String) {
                viewState.onSwipeRefresh(false)
                viewState.showMessage(message)
            }
        })
    }
}