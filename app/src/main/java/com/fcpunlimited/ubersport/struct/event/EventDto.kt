package com.fcpunlimited.ubersport.struct.event

import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.adapters.IListItem

data class EventDto(
        var eventName: String,
        var eventDate: Long,
        var eventAddress: String,
        var eventType: EventType
) : IListItem {
    override fun getLayoutId(): Int = R.layout.search_item
}