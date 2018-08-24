package com.fcpunlimited.ubersport.view.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.utils.BottomNavHelper
import com.fcpunlimited.ubersport.view.main.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavHelper.disableShiftMode(navigation)

        fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = SearchFragment.newInstance("", "")
            replaceFragment(fragment as SearchFragment)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_create -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_statistics -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
