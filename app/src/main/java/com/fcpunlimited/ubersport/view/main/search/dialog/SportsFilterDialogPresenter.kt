package com.fcpunlimited.ubersport.view.main.search.dialog

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack

@InjectViewState
class SportsFilterDialogPresenter(private val gameModel: GameModel,
                                  private val gamesLiveDataContainer: GamesLiveDataContainer)
    : MvpPresenter<SportsFilterDialogView>(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        val sports = gamesLiveDataContainer.sportsData.value
        if (sports == null || sports.isEmpty()) {
            gameModel.getSports(object : HttpEmptyResponseCallBack {
                override fun onFailure(message: String) {
                    viewState.showMessage(message)
                }
            })
        }
    }

    fun getGames() {
        gameModel.getGames(object : HttpEmptyResponseCallBack {
            override fun onResponse() {
                viewState.showMessage("Games updated")
            }

            override fun onFailure(message: String) {
                viewState.showMessage(message)
            }
        })
    }
}