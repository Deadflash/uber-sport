package com.fcpunlimited.ubersport.view.main.search

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.EventDto
import com.fcpunlimited.ubersport.utils.transformations.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_item.view.*


class SearchAdapter(private val events: List<EventDto>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventName.text = "Футбол"
        holder.tvEventTimeToStart.text = "Через 20 минут"
        holder.tvEventAddress.text = "Октябрьская улица дом 1"
        holder.tvEventDate.text = "1.09.2018"

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

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEventDate = itemView.tv_event_date
        val tvEventName = itemView.tv_event_name_header
        val tvEventAddress = itemView.tv_event_address
        val tvEventTimeToStart = itemView.tv_event_time_to_start
        val ivEventPlayerOne = itemView.iv_event_player_1
        val ivEventPlayerTwo = itemView.iv_event_player_2
        val ivEventPlayerThree = itemView.iv_event_player_3
    }
}