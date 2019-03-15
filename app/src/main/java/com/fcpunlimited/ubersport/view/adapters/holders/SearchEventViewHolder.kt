package com.fcpunlimited.ubersport.view.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_description.view.*
import kotlinx.android.synthetic.main.container_game_header.view.*
import kotlinx.android.synthetic.main.event_item.view.*
import kotlinx.android.synthetic.main.search_item.view.*

class SearchEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvEventDate = itemView.tv_calendar_date!!
    val tvEventName = itemView.tv_game_title!!
    val tvEventAddress = itemView.tv_game_address!!
    val tvAuthor = itemView.tv_author!!
    val tvSubtitle = itemView.tv_author_subtitle!!
    val tvParticipantsCount = itemView.tv_participants_count!!
    val progressBar = itemView.progressBar!!
    val tvGameTime = itemView.tv_game_time!!
    val ivSportIcon = itemView.iv_sport_icon!!
}