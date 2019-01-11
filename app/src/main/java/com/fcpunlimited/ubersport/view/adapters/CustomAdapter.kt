package com.fcpunlimited.ubersport.view.adapters

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.CreateEventDto
import com.fcpunlimited.ubersport.struct.event.EventDto
import com.fcpunlimited.ubersport.struct.user.ParticipantDto
import com.fcpunlimited.ubersport.view.adapters.holders.CreateEventViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.ParticipantViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.SearchEventViewHolder
import com.fcpunlimited.ubersport.view.description.DescriptionActivity
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

class CustomAdapter : BaseListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context

        return when (viewType) {
            R.layout.participant_item -> ParticipantViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.event_item -> CreateEventViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.search_item -> SearchEventViewHolder(inflateByViewType(context, viewType, parent))
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is ParticipantViewHolder -> bindParticipantsView(holder, position, items, context)
            is CreateEventViewHolder -> bindCreateEventView(holder, position, items, context)
            is SearchEventViewHolder -> bindSearchView(holder, position, items, context)
        }
    }

    private fun bindParticipantsView(holder: ParticipantViewHolder, position: Int,
                                     items: ArrayList<IListItem>, context: Context) {
        val participant = items[position] as ParticipantDto

        holder.tvParticipantName.text = participant.name
        holder.tvAboutParticipant.text = participant.about

        Picasso.get().load(R.drawable.avatar)
                .fit()
                .error(R.color.colorAccent)
                .into(holder.ivParticipantAvatar)
    }

    private fun bindCreateEventView(holder: CreateEventViewHolder, position: Int,
                                    items: ArrayList<IListItem>, context: Context) {
        val event = items[position] as CreateEventDto

        holder.tvEventName.text = event.eventName

        Picasso.get()
                .load(R.drawable.avatar)
                .fit()
                .error(R.color.colorAccent)
                .into(holder.ivEventIcon)

        holder.itemView.onClick { context.toast(event.eventName) }
    }

    private fun bindSearchView(holder: SearchEventViewHolder, position: Int,
                               items: ArrayList<IListItem>, context: Context) {
        val event = items[position] as EventDto

//        holder.tvEventName.text = event.eventName
//        holder.tvEventAddress.text = "Октябрьская улица дом 1"
//        holder.tvEventDate.text = "1.09.2018"

//        when (event.eventType) {
//            EventType.FOOTBALL -> {
//                holder.ivEventTypeHeader.image =
//                        ContextCompat.getDrawable(context, R.drawable.football_header)
//                holder.ivEventHeaderGradient.image =
//                        ContextCompat.getDrawable(context, R.drawable.rounded_border_rect)
//            }
//            else -> holder.ivEventTypeHeader.image =
//                    ContextCompat.getDrawable(context, R.drawable.football_header)
//        }

//        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
//                .fit()
//                .error(R.color.colorAccent)
//                .into(holder.ivEventPlayerOne)
//
//        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
//                .fit()
//                .into(holder.ivEventPlayerTwo)
//
//        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
//                .fit()
//                .into(holder.ivEventPlayerThree)

        holder.itemView.onClick {
            context
                    .startActivity(Intent(context, DescriptionActivity::class.java)
                            .singleTop())
        }
    }
}