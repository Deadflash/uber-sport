package com.fcpunlimited.ubersport.di.game

import androidx.lifecycle.MutableLiveData
import com.fcpunlimited.ubersport.struct.game.ActiveGamesDto

data class ActiveGamesLiveDataContainer(val activeGamesData: MutableLiveData<List<ActiveGamesDto>>) {
}