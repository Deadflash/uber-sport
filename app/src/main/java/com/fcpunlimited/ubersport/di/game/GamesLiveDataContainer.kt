package com.fcpunlimited.ubersport.di.game

import androidx.lifecycle.MutableLiveData
import com.fcpunlimited.ubersport.SportsQuery
import com.fcpunlimited.ubersport.struct.game.GameDto

data class GamesLiveDataContainer(val gameData: MutableLiveData<List<GameDto>>, val sportsData: MutableLiveData<List<SportsQuery.Sport>>)