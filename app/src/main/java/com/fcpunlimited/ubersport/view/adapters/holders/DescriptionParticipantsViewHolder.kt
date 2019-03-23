package com.fcpunlimited.ubersport.view.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.description_participant_item.view.*

class DescriptionParticipantsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivDescriptionParticipant = view.iv_participant_avatar!!
    val tvParticipantName = view.tv_participant_name!!
    val btExcludeParticipant = view.bt_exclude_participant!!
    val excludeParticipantLayout = view.excludeParticipantLayout
}