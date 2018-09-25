package com.fcpunlimited.ubersport.view.description

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.fcpunlimited.ubersport.view.description.SearchPageFragment

class DescriptionViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> SearchPageFragment()
        1 -> SearchPageFragment()
        2 -> SearchPageFragment()
        else -> SearchPageFragment()
    }

    override fun getCount(): Int = 3

}