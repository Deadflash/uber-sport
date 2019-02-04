package com.fcpunlimited.ubersport.view.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.CREATE_EVENT_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.PROFILE_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.main.create_event.CreateEventMvpFragment
import com.fcpunlimited.ubersport.view.main.profile.ProfileMvpFragment
import com.fcpunlimited.ubersport.view.main.search.SearchMvpFragment

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainView>() {

    private lateinit var searchFragment: SearchMvpFragment
    private lateinit var createEventFragment: CreateEventMvpFragment
    private lateinit var profileFragment: ProfileMvpFragment

    fun initFragments() {
        searchFragment = SearchMvpFragment.newInstance("", "")
        createEventFragment = CreateEventMvpFragment.newInstance("", "")
        profileFragment = ProfileMvpFragment.newInstance("", "")
    }

    fun showFragmentByTag(tag: String) {
        val fragment = when (tag) {
            SEARCH_FRAGMENT_TAG -> searchFragment
            CREATE_EVENT_FRAGMENT_TAG -> createEventFragment
            PROFILE_FRAGMENT_TAG -> profileFragment
            else -> searchFragment
        }
        viewState.replaceFragmentAndMenu(fragment)
    }
}