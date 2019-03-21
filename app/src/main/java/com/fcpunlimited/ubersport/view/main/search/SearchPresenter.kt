package com.fcpunlimited.ubersport.view.main.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.game.GameContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesResponseCallBack

@InjectViewState
class SearchPresenter(private val gameModel: GameModel, private val gameContainer: GameContainer)
    : MvpPresenter<SearchView>(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateSearchView() {
        if (gameContainer.gameData.value == null) {
            loadGames()
        }
    }

    fun loadGames() {
        viewState.onSwipeRefresh(true)
        gameModel.getGames(object : GamesResponseCallBack {
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