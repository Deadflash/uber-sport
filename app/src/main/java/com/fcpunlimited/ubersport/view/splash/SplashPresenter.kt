package com.fcpunlimited.ubersport.view.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.struct.auth.UberAuth

@InjectViewState
class SplashPresenter(private val userModel: UserModel, private val gameModel: GameModel) : MvpPresenter<SplashView>(), HttpResponseCallBack<UberAuth> {

    override fun attachView(view: SplashView?) {
        super.attachView(view)
//        userModel.getToken(this)
        userModel.login(this)
    }

    override fun onResponse(data: UberAuth) {
        viewState.showMessage(data.accessToken)
        viewState.setAuthorized(true)
    }

    override fun onFailure(message: String) {
        viewState.showMessage(message)
        viewState.setAuthorized(true)
    }
}