package com.fcpunlimited.ubersport.view.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.CreateEventDto
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.struct.game.GameParticipantsDto
import com.fcpunlimited.ubersport.struct.user.ParticipantDto
import com.fcpunlimited.ubersport.utils.Constants.DATE_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DATE_HOUR_FORMAT
import com.fcpunlimited.ubersport.utils.getSportIconByName
import com.fcpunlimited.ubersport.view.adapters.holders.CreateEventViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.DescriptionParticipantsViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.ParticipantViewHolder
import com.fcpunlimited.ubersport.view.adapters.holders.SearchEventViewHolder
import com.fcpunlimited.ubersport.view.main.search.SearchFragment
import com.squareup.picasso.Picasso
import org.jetbrains.anko.image
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter : BaseListAdapter(), LifecycleObserver {

    private var lifecycleOwner: LifecycleOwner? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun addListener(owner: LifecycleOwner) {
        this.lifecycleOwner = owner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeListener() {
        lifecycleOwner = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context

        return when (viewType) {
            R.layout.participant_item -> ParticipantViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.event_item -> CreateEventViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.search_item -> SearchEventViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.description_participant_item -> DescriptionParticipantsViewHolder(inflateByViewType(context, viewType, parent))
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is ParticipantViewHolder -> bindParticipantsView(holder, position, items, context)
            is CreateEventViewHolder -> bindCreateEventView(holder, position, items, context)
            is SearchEventViewHolder -> bindSearchView(holder, position, items, context)
            is DescriptionParticipantsViewHolder -> bindDescriptionParticipantsView(holder, position, items, context)
        }
    }

    private fun bindDescriptionParticipantsView(holder: DescriptionParticipantsViewHolder,
                                                position: Int, items: ArrayList<IListItem>,
                                                context: Context) {
        val participant = (items[position] as GameParticipantsDto).participant
        holder.apply {
            if (participant.__typename() == "STUB") {
                btExcludeParticipant.visibility = View.GONE
                loadImage(R.drawable.add, ivDescriptionParticipant)
                tvParticipantName.text = "Add player"
                return
            }
            tvParticipantName.text = participant.nickname()
            loadImage(R.drawable.avatar, ivDescriptionParticipant)
            btExcludeParticipant.setOnClickListener { context.toast("exclude participant ${participant.nickname()}") }
        }
    }

    private fun bindParticipantsView(holder: ParticipantViewHolder, position: Int,
                                     items: ArrayList<IListItem>, context: Context) {
        val participant = items[position] as ParticipantDto
        holder.apply {
            tvParticipantName.text = participant.name
            tvAboutParticipant.text = participant.about
            loadImage(R.drawable.avatar, ivParticipantAvatar)
        }
    }

    private fun bindCreateEventView(holder: CreateEventViewHolder, position: Int,
                                    items: ArrayList<IListItem>, context: Context) {
        val event = items[position] as CreateEventDto

        holder.apply {
            tvEventName.text = event.eventName
            itemView.setOnClickListener { context.toast(event.eventName) }
            loadImage(R.drawable.avatar, ivEventIcon)
        }
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
                    (lifecycleOwner as SearchFragment).navigate((items[position] as GameDto).game)
                }
            }
        }
    }

    private fun loadImage(imageId: Int, imageView: ImageView) {
        Picasso.get()
                .load(imageId)
                .fit()
                .error(R.color.red_light)
                .into(imageView)
    }
}