package com.fcpunlimited.ubersport

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fcpunlimited.ubersport.di.api.HttpRequestClients
import com.fcpunlimited.ubersport.di.api.HttpRequestClientsImpl
import com.fcpunlimited.ubersport.di.game.GamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.GameFilterContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.user.UserModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {

    private val myModules = module {
        single<HttpRequestClients> { HttpRequestClientsImpl() }
        single { GamesLiveDataContainer(MutableLiveData(), MutableLiveData()) }
        single { GameFilterContainer(this@App) }
        single { UserModel(get()) }
        single { GameModel(get(), get(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger()
            modules(myModules)
        }
    }
}