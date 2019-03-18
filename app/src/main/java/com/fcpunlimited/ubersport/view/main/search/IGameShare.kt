package com.fcpunlimited.ubersport.view.main.search

import com.fcpunlimited.ubersport.GamesQuery

interface IGameShare {
    interface IGameProvider {
        fun provideGame(game: GamesQuery.Game)
    }

    interface IGameConsumer {
        fun consumeGame(): GamesQuery.Game?
    }
}