package com.fcpunlimited.ubersport.view.main.search

import com.arellomobile.mvp.MvpView

interface SearchView : MvpView {
    fun showSwipeRefresh()
    fun hideSwipeRefresh()
}