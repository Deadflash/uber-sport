package com.fcpunlimited.ubersport.di.game

import androidx.lifecycle.MutableLiveData
import com.fcpunlimited.ubersport.struct.game.GameDto

data class GameContainer(val gameData: MutableLiveData<List<GameDto>>)