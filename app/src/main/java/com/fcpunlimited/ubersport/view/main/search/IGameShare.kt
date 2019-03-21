package com.fcpunlimited.ubersport.view.main.search

import com.fcpunlimited.ubersport.fragment.GameFragment

interface IGameShare {
    interface IGameProvider {
        fun provideGame(game: GameFragment)
    }

    interface IGameConsumer {
        fun consumeGame(): GameFragment?
    }
}