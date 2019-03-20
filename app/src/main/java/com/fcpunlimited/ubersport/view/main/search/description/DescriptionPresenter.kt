package com.fcpunlimited.ubersport.view.main.search.description

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.user.UserModel

@InjectViewState
class DescriptionPresenter(private val gameModel: GameModel, private val userModel: UserModel)
    : MvpPresenter<DescriptionView>() {

    fun joinGame(gameId: String) {
        gameModel.joinGame(userModel.getUserId(), gameId, object : HttpResponseCallBack<String> {
            override fun onOkResponse(data: String) {
                viewState.joinedToGame()
            }

            override fun onErrorResponse(message: String) {
                viewState.showMessage(message)
            }
        })
    }

    fun leaveGame(gameId: String) {
        gameModel.leaveGame(userModel.getUserId(), gameId, object : HttpResponseCallBack<String> {
            override fun onOkResponse(data: String) {
                viewState.leavedGame()
            }

            override fun onErrorResponse(message: String) {
                viewState.showMessage(message)
            }
        })
    }
}