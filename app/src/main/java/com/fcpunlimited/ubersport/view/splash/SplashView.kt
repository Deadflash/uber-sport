package com.fcpunlimited.ubersport.view.splash

import com.arellomobile.mvp.MvpView
import com.fcpunlimited.ubersport.view.IBaseView

interface SplashView : MvpView, IBaseView {
    fun setAuthorized(isAuthorized: Boolean)
}