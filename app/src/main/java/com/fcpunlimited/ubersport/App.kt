package com.fcpunlimited.ubersport

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.fcpunlimited.ubersport.view.main.search.SearchPresenter
import okhttp3.OkHttpClient
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext


class App : Application() {

    private val myModules = applicationContext {
        bean { SearchPresenter() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(myModules))
    }
}