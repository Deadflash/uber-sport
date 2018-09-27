package com.fcpunlimited.ubersport.struct.user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.participant_item.view.*

class ParticipantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivParticipantAvatar = view.iv_participant_avatar
    val tvParticipantName = view.tv_participant_name
    val tvAboutParticipant = view.tv_about_participant
}