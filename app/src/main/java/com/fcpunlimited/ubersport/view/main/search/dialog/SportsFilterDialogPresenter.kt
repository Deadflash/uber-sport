package com.fcpunlimited.ubersport.view.main.search.dialog

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack

@InjectViewState
class SportsFilterDialogPresenter(private val gameModel: GameModel) : MvpPresenter<SportsFilterDialogView>() {

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