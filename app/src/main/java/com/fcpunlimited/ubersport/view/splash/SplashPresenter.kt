package com.fcpunlimited.ubersport.view.splash

import com.arellomobile.mvp.MvpPresenter

class SplashPresenter : MvpPresenter<SplashView>() {

    override fun attachView(view: SplashView?) {
        super.attachView(view)
        view?.setAuthorized(true)
    }
}