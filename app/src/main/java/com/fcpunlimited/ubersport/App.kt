package com.fcpunlimited.ubersport

import android.app.Application
import com.fcpunlimited.ubersport.di.GraphQlClient
import com.fcpunlimited.ubersport.di.GraphQlClientImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {

    private val myModules = module {
//        single { SearchPresenter() }
        single<GraphQlClient> { GraphQlClientImpl() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger()
            modules(myModules)
        }
    }
}