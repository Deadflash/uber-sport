package com.fcpunlimited.ubersport.view.main

import com.arellomobile.mvp.MvpView
import com.fcpunlimited.ubersport.view.BaseMvpFragment

interface MainView : MvpView {

    fun replaceFragmentAndMenu(fragment: BaseMvpFragment)
}