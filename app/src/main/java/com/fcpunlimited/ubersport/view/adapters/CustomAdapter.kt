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
import com.fcpunlimited.ubersport.utils.SportType
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

        holder.tvEventName.text = game.name()
        holder.tvEventDate.text = SimpleDateFormat("EEE dd-MMM h:mm", Locale.ROOT)
                .format(game.dateStart().toLong())
        game.location()?.let { holder.tvEventAddress.text = it.address() }
        game.author()?.let {
            holder.tvAuthor.text = it.nickname()
            holder.tvSubtitle.text = "${it.firstName()} ${it.lastName()}"
        }
        game.length()?.let { holder.tvGameTime.text = SimpleDateFormat("HH:mm", Locale.ROOT).format(it) }
        game.participants()?.let {
            val participantsLimit = game.participantsLimit()?.toInt() ?: 0
            holder.progressBar.max = participantsLimit
            holder.progressBar.progress = it.size
            holder.tvParticipantsCount.text = "${it.size}/$participantsLimit"
        }
        game.sport()?.let {
            when (it.name()) {
                SportType.Football.name -> {
                    holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football)
                }
                SportType.Basketball.name -> {
                    holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_baseball)

                }
                SportType.Bicycle.name -> {
                    holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football)

                }
                SportType.Paintball.name -> {
                    holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football)

                }
                SportType.Tennis.name -> {
                    holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_tennisball)

                }
                SportType.Volleyball.name -> {
                    holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_tennisball)
                }
                else -> holder.ivSportIcon.image = ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football)
            }
        }

        holder.itemView.setOnClickListener {
            iNavigation?.navigate((items[position] as GameDto).copy().game)
        }
    }
}