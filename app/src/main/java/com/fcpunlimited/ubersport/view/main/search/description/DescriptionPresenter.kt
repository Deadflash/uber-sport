package com.fcpunlimited.ubersport.view.main.search.description

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.JoinGameMutation
import com.fcpunlimited.ubersport.LeaveGameMutation
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesResponseCallBack

@InjectViewState
class DescriptionPresenter(private val gameModel: GameModel)
    : MvpPresenter<DescriptionView>() {

    fun joinGame(gameId: String) {
        gameModel.joinGame(gameId, object : HttpResponseCallBack<JoinGameMutation.Data> {
            override fun onResponse(data: JoinGameMutation.Data) {
                viewState.joinedGame(data.joinGame().fragments().gameFragment())
                reloadGames()
            }

            override fun onFailure(message: String) {
                viewState.showMessage(message)
            }
        })
    }

    fun leaveGame(gameId: String) {
        gameModel.leaveGame(gameId, object : HttpResponseCallBack<LeaveGameMutation.Data> {
            override fun onResponse(data: LeaveGameMutation.Data) {
                viewState.leavedGame(data.leaveGame().fragments().gameFragment())
                reloadGames()
            }

            override fun onFailure(message: String) {
                viewState.showMessage(message)
            }
        })
    }

    private fun reloadGames() {
        gameModel.getGames(object : GamesResponseCallBack {
            override fun onResponse() {
                viewState.showMessage("Games updated")
            }

            override fun onFailure(message: String) {
                viewState.showMessage(message)
            }
        })
    }
}