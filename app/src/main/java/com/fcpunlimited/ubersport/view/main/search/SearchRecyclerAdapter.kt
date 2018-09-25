package com.fcpunlimited.ubersport.view.main.search

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.EventDto
import com.fcpunlimited.ubersport.struct.EventType
import com.fcpunlimited.ubersport.view.description.DescriptionActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*
import org.jetbrains.anko.image


class SearchRecyclerAdapter(private val events: List<EventDto>) : RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventName.text = events[position].eventName
//        holder.tvEventTimeToStart.text = "Через 20 минут"
        holder.tvEventAddress.text = "Октябрьская улица дом 1"
        holder.tvEventDate.text = "1.09.2018"

        when (events[position].eventType) {
            EventType.FOOTBALL -> {
                holder.ivEventTypeHeader.image =
                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.football_header)
                holder.ivEventHeaderGradient.image =
                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.search_football_header_rect)
            }
//            EventType.ALCO_TRASH -> {
//                holder.ivEventTypeHeader.image =
//                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.alco_trash)
//                holder.ivEventHeaderGradient.image =
//                        ContextCompat.getDrawable(holder.itemView.context, R.drawable.search_alco_trash_header_rect)
//
//            }
            else -> holder.ivEventTypeHeader.image =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.football_header)
        }

//        Picasso.get().load(R.drawable.football_header)
//                .fit()
//                .into(holder.ivEventTypeHeader)

        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
                .fit()
                .error(R.color.colorAccent)
                .into(holder.ivEventPlayerOne)


        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
                .fit()
                .into(holder.ivEventPlayerTwo)

        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
                .fit()
                .into(holder.ivEventPlayerThree)

        holder.itemView.onClick { holder.itemView.context.startActivity<DescriptionActivity>() }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEventDate = itemView.tv_event_date
        val tvEventName = itemView.tv_event_name_header
        val tvEventAddress = itemView.tv_event_address
        //        val tvEventTimeToStart = itemView.tv_event_time_to_start
        val ivEventPlayerOne = itemView.iv_event_player_1
        val ivEventPlayerTwo = itemView.iv_event_player_2
        val ivEventPlayerThree = itemView.iv_event_player_3
        val ivEventTypeHeader = itemView.iv_event_type_header
        val ivEventHeaderGradient = itemView.iv_search_header_gradient
    }
}