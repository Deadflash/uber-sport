package com.fcpunlimited.ubersport.view.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainView>(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initFragments(owner: LifecycleOwner) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun anyLifecycleEvent(owner: LifecycleOwner, event: Lifecycle.Event){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyLifecycle(){

    }
}