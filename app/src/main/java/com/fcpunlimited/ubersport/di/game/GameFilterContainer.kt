package com.fcpunlimited.ubersport.di.game

import android.content.Context.MODE_PRIVATE
import com.fcpunlimited.ubersport.App
import com.fcpunlimited.ubersport.type.GameFiltersInput
import com.fcpunlimited.ubersport.type.GameStatus
import com.fcpunlimited.ubersport.utils.Constants.SPORT_IDS
import com.fcpunlimited.ubersport.utils.Constants.UBER_SPORT_PREFS

class GameFilterContainer(app: App) {

    private val prefs = app.getSharedPreferences(UBER_SPORT_PREFS, MODE_PRIVATE)

    private val filterBuilder: GameFiltersInput.Builder = GameFiltersInput.builder()

    fun getFilterBuilder() = filterBuilder

    fun getFilter(): GameFiltersInput = filterBuilder
            .sportId(prefs.getStringSet(SPORT_IDS, mutableSetOf()).firstOrNull())!!
            .status(GameStatus.PENDING).build()
}