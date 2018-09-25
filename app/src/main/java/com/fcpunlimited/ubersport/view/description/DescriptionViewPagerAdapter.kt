package com.fcpunlimited.ubersport.view.description

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class DescriptionViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> SearchPageFragment()
        1 -> SearchPageFragment()
        2 -> SearchPageFragment()
        else -> SearchPageFragment()
    }

    override fun getCount(): Int = 3

}