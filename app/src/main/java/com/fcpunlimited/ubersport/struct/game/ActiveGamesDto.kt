package com.fcpunlimited.ubersport.struct.game

import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.view.adapters.IListItem

data class ActiveGamesDto(var game: GameFragment) : IListItem {
    override fun getLayoutId(): Int = R.layout.active_game_item
}