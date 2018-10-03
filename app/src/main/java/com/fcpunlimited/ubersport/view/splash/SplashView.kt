package com.fcpunlimited.ubersport.view.splash

import com.arellomobile.mvp.MvpView

interface SplashView: MvpView {
    fun setAuthorized(isAuthorized: Boolean)
}