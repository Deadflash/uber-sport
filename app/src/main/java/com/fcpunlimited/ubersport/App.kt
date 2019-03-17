package com.fcpunlimited.ubersport

import android.app.Application
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.gql.GraphQlClient
import com.fcpunlimited.ubersport.di.gql.GraphQlClientImpl
import com.fcpunlimited.ubersport.di.user.UserModel
import com.fcpunlimited.ubersport.view.description.DescriptionActivityParcel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App : Application() {

    private val myModules = module {
        single<GraphQlClient> { GraphQlClientImpl() }
        single { UserModel(get()) }
        single { GameModel(get()) }
        single { DescriptionActivityParcel() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger()
            modules(myModules)
        }
    }
}