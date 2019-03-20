package com.fcpunlimited.ubersport.view.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.api.HttpResponseCallBack
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.struct.auth.Token

@InjectViewState
class SplashPresenter(private val userModel: UserModel) : MvpPresenter<SplashView>(), HttpResponseCallBack<Token> {

    override fun attachView(view: SplashView?) {
        super.attachView(view)
        if (!userModel.hasToken()) {
            userModel.getToken(this)
        } else {
            viewState?.setAuthorized(true)
        }
    }

    override fun onOkResponse(data: Token) {
        viewState?.showMessage(data.accessToken)
        viewState?.setAuthorized(true)
    }

    override fun onErrorResponse(message: String) {
        viewState?.showMessage(message)
        viewState?.setAuthorized(true)
    }
}