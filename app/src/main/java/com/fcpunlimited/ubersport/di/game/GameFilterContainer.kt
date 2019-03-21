package com.fcpunlimited.ubersport.di.game

import com.fcpunlimited.ubersport.type.GameFiltersInput
import com.fcpunlimited.ubersport.type.GameStatus

class GameFilterContainer {

    private val filterBuilder: GameFiltersInput.Builder = GameFiltersInput.builder()

    fun getFilterBuilderL() = filterBuilder

    fun getFilter(): GameFiltersInput = filterBuilder.status(GameStatus.PENDING).build()
}