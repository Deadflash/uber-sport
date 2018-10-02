package com.fcpunlimited.ubersport.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.CREATE_EVENT_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.PROFILE_FRAGMENT_TAG
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.SEARCH_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseMvpActivity(), MainActivityView {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        presenter.initFragments()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            presenter.showFragmentByTag(SEARCH_FRAGMENT_TAG)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView
            .OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_search -> {
                        presenter.showFragmentByTag(SEARCH_FRAGMENT_TAG)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_create -> {
                        presenter.showFragmentByTag(CREATE_EVENT_FRAGMENT_TAG)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_profile -> {
                        presenter.showFragmentByTag(PROFILE_FRAGMENT_TAG)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }

    override fun replaceFragmentAndMenu(fragment: BaseMvpFragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        toolbar?.menu?.clear()
        (fragment as BaseMvpFragment).getFragmentMenu()
                ?.let { it1 -> toolbar?.inflateMenu(it1) }
        super.onAttachFragment(fragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        supportFragmentManager.findFragmentById(R.id.fragment_container)
                ?.let {
                    (it as BaseMvpFragment).getFragmentMenu()
                            ?.let { it1 -> menuInflater.inflate(it1, menu) }
                }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_filter -> {
                    toast("filter")
                    true
                }
                R.id.action_create_event -> {
                    toast("createEvent")
                    true
                }
                else -> {
                    toast("Unknown item clicked")
                    false
                }
            }
}
