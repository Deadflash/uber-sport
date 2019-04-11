package com.fcpunlimited.ubersport.di.game

import android.content.Context.MODE_PRIVATE
import com.fcpunlimited.ubersport.App
import com.fcpunlimited.ubersport.type.GameFiltersInput
import com.fcpunlimited.ubersport.type.GameStatus
import com.fcpunlimited.ubersport.utils.Constants.SPORT_IDS
import com.fcpunlimited.ubersport.utils.Constants.UBER_SPORT_PREFS

class GameFilterContainer(private val app: App) {

    private val filterBuilder: GameFiltersInput.Builder = GameFiltersInput.builder()

    fun getFilterBuilder() = filterBuilder

    fun getUserFilterSportIds(): Set<String> = app
            .getSharedPreferences(UBER_SPORT_PREFS, MODE_PRIVATE)
            .getStringSet(SPORT_IDS, mutableSetOf())!!

    fun getFilter(): GameFiltersInput {
        return filterBuilder
                .sportId(getUserFilterSportIds().firstOrNull())
                .status(GameStatus.PENDING).build()
    }

    fun addUserFilterSportId(sportId: String) {
        var sportIds = getUserFilterSportIds()
        sportIds = sportIds.plusElement(sportId)
        val prefs = app.getSharedPreferences(UBER_SPORT_PREFS, MODE_PRIVATE)
        prefs.edit().putStringSet(SPORT_IDS, sportIds).apply()
    }

    fun removeUserFilterSportId(sportId: String) {
        var sportIds = getUserFilterSportIds()
        if (sportIds.contains(sportId)) {
            val prefs = app.getSharedPreferences(UBER_SPORT_PREFS, MODE_PRIVATE)
            sportIds = sportIds.minusElement(sportId)
            prefs.edit().putStringSet(SPORT_IDS, sportIds).apply()
        }
    }

    fun updateUserFilterSportIds(sportIds: Set<String>) {
        val prefs = app.getSharedPreferences(UBER_SPORT_PREFS, MODE_PRIVATE)
        prefs.edit().putStringSet(SPORT_IDS, sportIds).apply()
    }
}