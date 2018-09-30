package com.fcpunlimited.ubersport.view.main

import android.os.Bundle
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseFragment
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.main.create_event.CreateEventFragment
import com.fcpunlimited.ubersport.view.main.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity() {

    private var searchFragment: SearchFragment? = null
    private var createEventFragment: CreateEventFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragments()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val currentFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_container) as BaseFragment?

        if (currentFragment == null) {
            replaceFragment(searchFragment as BaseFragment)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView
            .OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> {
                replaceFragment(searchFragment as BaseFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_create -> {
                replaceFragment(createEventFragment as BaseFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun initFragments() {
        searchFragment = if (searchFragment == null)
            SearchFragment.newInstance("", "")
        else searchFragment

        createEventFragment = if (createEventFragment == null)
            CreateEventFragment.newInstance("", "")
        else createEventFragment
    }

    private fun replaceFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
