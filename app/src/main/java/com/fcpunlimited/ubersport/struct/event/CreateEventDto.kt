package com.fcpunlimited.ubersport.struct.event

import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.utils.adapter.IListItem

data class CreateEventDto(
        var eventName: String,
        var eventIcon: Int
) : IListItem {
    override fun getLayoutId(): Int = R.layout.event_item
}