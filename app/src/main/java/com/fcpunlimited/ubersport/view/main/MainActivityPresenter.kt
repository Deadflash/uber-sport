package com.fcpunlimited.ubersport.view.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.di.gql.GraphQlClient
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.CREATE_EVENT_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.PROFILE_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.main.create_event.CreateEventMvpFragment
import com.fcpunlimited.ubersport.view.main.profile.ProfileMvpFragment
import com.fcpunlimited.ubersport.view.main.search.SearchMvpFragment

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