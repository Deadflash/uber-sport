package com.fcpunlimited.ubersport.view.main

import com.arellomobile.mvp.MvpView
import com.fcpunlimited.ubersport.view.BaseMvpFragment

interface MainActivityView : MvpView {

    fun replaceFragmentAndMenu(fragment: BaseMvpFragment)
}