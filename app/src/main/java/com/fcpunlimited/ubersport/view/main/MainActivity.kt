package com.fcpunlimited.ubersport.view.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity(), MainView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MAIN_PRESENTER")
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(presenter)
        val navController = Navigation
                .findNavController(this@MainActivity, R.id.main_nav_host_fragment)

        NavigationUI.setupWithNavController(navigation, navController)
    }
}
