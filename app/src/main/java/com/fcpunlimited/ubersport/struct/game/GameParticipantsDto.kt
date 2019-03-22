package com.fcpunlimited.ubersport.struct.game

import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.fragment.GameFragment
import com.fcpunlimited.ubersport.view.adapters.IListItem

data class GameParticipantsDto(var participant: GameFragment.Participant) : IListItem {
    override fun getLayoutId(): Int = R.layout.description_participant_item
}