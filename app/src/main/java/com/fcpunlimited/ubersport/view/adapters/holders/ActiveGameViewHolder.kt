package com.fcpunlimited.ubersport.view.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.active_game_item.view.*

class ActiveGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvGameDate = itemView.tv_game_date!!
    val шмЫзщкеШсщт = itemView.iv_sport_icon!!
    val tvEventName = itemView.tv_event_name!!
    val ivAvatar = itemView.iv_avatar!!
    val tvGameAuthor = itemView.tv_game_author!!
}