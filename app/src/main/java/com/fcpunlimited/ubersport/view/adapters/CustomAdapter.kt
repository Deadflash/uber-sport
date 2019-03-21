package com.fcpunlimited.ubersport.view.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.CreateEventDto
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.struct.user.ParticipantDto
import com.fcpunlimited.ubersport.utils.Constants.DATE_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DATE_HOUR_FORMAT
import com.fcpunlimited.ubersport.utils.getSportIconByName
import com.fcpunlimited.ubersport.view.adapters.holders.CreateEventViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.ParticipantViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.SearchEventViewHolder
import com.fcpunlimited.ubersport.view.main.search.SearchFragment
import com.squareup.picasso.Picasso
import org.jetbrains.anko.image
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter : BaseListAdapter(), LifecycleObserver {

    private var iNavigation: INavigation? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun addListener(owner: LifecycleOwner) {
        if (owner is SearchFragment)
            this.iNavigation = owner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeListener() {
        iNavigation = null
    }

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

        holder.itemView.setOnClickListener { context.toast(event.eventName) }
    }

    private fun bindSearchView(holder: SearchEventViewHolder, position: Int,
                               items: ArrayList<IListItem>, context: Context) {

        val game = (items[position] as GameDto).game
        holder.apply {
            game.apply {
                tvEventName.text = name()
                tvEventDate.text = SimpleDateFormat(DATE_FORMAT, Locale.ROOT)
                        .format(dateStart().toLong())
                location()?.apply { tvEventAddress.text = address() }
                author()?.apply {
                    tvAuthor.text = nickname()
                    tvSubtitle.text = "${firstName()} ${lastName()}"
                }
                length()?.let { tvGameTime.text = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.ROOT).format(it) }
                participants()?.apply {
                    val participantsLimit = participantsLimit()?.toInt() ?: 0
                    progressBar.max = participantsLimit
                    progressBar.progress = size
                    tvParticipantsCount.text = "$size/$participantsLimit"
                }
                sport()?.apply {
                    ivSportIcon.image = ContextCompat.getDrawable(context, getSportIconByName(name()))
                }

                itemView.setOnClickListener {
                    iNavigation?.navigate((items[position] as GameDto).game)
                }
            }
        }
    }
}