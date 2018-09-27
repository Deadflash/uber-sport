package com.fcpunlimited.ubersport.struct.event

data class EventDto(
        var eventName: String,
        var eventDate: Long,
        var eventAddress: String,
        var eventType: EventType
)