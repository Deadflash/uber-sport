package com.fcpunlimited.ubersport.view.main.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

@InjectViewState
class SearchPresenter : MvpPresenter<SearchView>() {

    fun onSwipeRefresh() {
        viewState.showSwipeRefresh()
        doAsync {
            Thread.sleep(7000)
            uiThread {
                viewState.hideSwipeRefresh()
            }
        }
    }
}