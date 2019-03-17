package com.fcpunlimited.ubersport.view.adapters

import com.fcpunlimited.ubersport.GamesQuery

interface INavigation {
    fun navigate(game: GamesQuery.Game)
}