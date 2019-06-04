package com.fcpunlimited.ubersport.di.game

import androidx.lifecycle.MutableLiveData
import com.fcpunlimited.ubersport.struct.game.ActiveGamesDto
import com.fcpunlimited.ubersport.struct.game.GamesHistoryDto

data class GamesHistoryLiveDataContainer(val activeGamesData: MutableLiveData<List<GamesHistoryDto>>) {
}