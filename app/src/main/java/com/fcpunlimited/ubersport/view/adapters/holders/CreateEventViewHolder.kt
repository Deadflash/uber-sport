package com.fcpunlimited.ubersport.view.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_item.view.*

class CreateEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivEventIcon = view.iv_event_icon!!
    val tvEventName = view.tv_event_name!!
}