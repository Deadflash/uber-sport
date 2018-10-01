package com.fcpunlimited.ubersport.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.CreateEventDto
import com.fcpunlimited.ubersport.struct.user.ParticipantDto
import com.fcpunlimited.ubersport.utils.adapter.ListAdapter
import com.fcpunlimited.ubersport.view.adapters.holders.CreateEventViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.ParticipantViewHolder
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class CustomAdapter : ListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context

        return when (viewType) {
            R.layout.participant_item -> ParticipantViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.event_item -> CreateEventViewHolder(inflateByViewType(context, viewType, parent))
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ParticipantViewHolder -> {
                val participant = items[position] as ParticipantDto

                holder.tvParticipantName.text = participant.name
                holder.tvAboutParticipant.text = participant.about

                Picasso.get().load(R.drawable.pahom)
                        .fit()
                        .error(R.color.colorAccent)
                        .into(holder.ivParticipantAvatar)
            }
            is CreateEventViewHolder -> {
                val event = items[position] as CreateEventDto

                holder.tvEventName.text = event.eventName

                Picasso.get()
                        .load(R.drawable.pahom)
                        .fit()
                        .error(R.color.colorAccent)
                        .into(holder.ivEventIcon)

                holder.itemView.onClick { holder.itemView.context.toast(event.eventName) }
            }
        }
    }
}