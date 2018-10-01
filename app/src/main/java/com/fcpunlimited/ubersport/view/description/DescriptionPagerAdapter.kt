package com.fcpunlimited.ubersport.view.description

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fcpunlimited.ubersport.view.description.pages.DescriptionPage
import com.fcpunlimited.ubersport.view.description.pages.DescriptionParticipantsFragment

class DescriptionPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> DescriptionParticipantsFragment.newInstance("", "")
        1 -> DescriptionPage.newInstance("", "")
        else -> DescriptionPage.newInstance("", "")
    }

    override fun getCount(): Int = 2

}