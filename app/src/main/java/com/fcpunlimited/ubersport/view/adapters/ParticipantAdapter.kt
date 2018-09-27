package com.fcpunlimited.ubersport.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.user.ParticipantDto
import com.fcpunlimited.ubersport.struct.user.ParticipantViewHolder
import com.fcpunlimited.ubersport.utils.adapter.ListAdapter
import com.squareup.picasso.Picasso

class ParticipantAdapter : ListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context

        return when (viewType) {
            R.layout.participant_item -> ParticipantViewHolder(inflateByViewType(context, viewType, parent))

            else -> throw RuntimeException("Unable response ")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ParticipantViewHolder -> {
                val participant = items[position] as ParticipantDto

                holder.tvParticipantName.text = participant.name
                holder.tvAboutParticipant.text = participant.about

                Picasso.get().load(R.drawable.pahom)
                        .fit()
                        .error(R.color.colorAccent)
                        .into(holder.ivParticipantAvatar)
            }
        }
    }
}