package com.fcpunlimited.ubersport.struct.user

import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.utils.adapter.IListItem

data class ParticipantDto(
        var name: String,
        var avatarUrl: String,
        var about: String
) : IListItem {
    override fun getLayoutId(): Int = R.layout.participant_item
}