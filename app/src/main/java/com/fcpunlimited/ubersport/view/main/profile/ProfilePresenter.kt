package com.fcpunlimited.ubersport.view.main.profile

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.game.ActiveGamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack

@InjectViewState
class ProfilePresenter(private val gameModel: GameModel,
                       private val activeGamesLiveDataContainer: ActiveGamesLiveDataContainer)
    : MvpPresenter<ProfileView>(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onCreateProfileView() {
        gameModel.getActiveGames(object : HttpEmptyResponseCallBack {
            override fun onFailure(message: String) {
                viewState.showMessage(message)
            }
        })
    }

}