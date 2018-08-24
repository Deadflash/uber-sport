package com.fcpunlimited.ubersport

import android.app.Application
import com.fcpunlimited.ubersport.view.main.presenter.SearchPresenter
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext


class App : Application() {

    val myModules = applicationContext {
        bean { SearchPresenter() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(myModules))
    }
}