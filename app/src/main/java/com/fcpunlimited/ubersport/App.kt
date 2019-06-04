package com.fcpunlimited.ubersport

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.di.api.HttpRequestClientsImpl
import com.fcpunlimited.ubersport.di.game.*
import com.fcpunlimited.ubersport.di.user.UserModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {

    private val myModules = module {
        single<HttpRequestClients> { HttpRequestClientsImpl() }
        single { GamesLiveDataContainer(MutableLiveData(), MutableLiveData()) }
        single { ActiveGamesLiveDataContainer(MutableLiveData()) }
        single { GamesHistoryLiveDataContainer(MutableLiveData()) }
        single { GameFilterContainer(this@App) }
        single { UserModel(get()) }
        single { GameModel(get(), get(), get(), get(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger()
            modules(myModules)
        }
    }
}