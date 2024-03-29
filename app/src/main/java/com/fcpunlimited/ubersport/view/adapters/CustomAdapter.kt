package com.fcpunlimited.ubersport.view.adapters

import android.content.Context
import android.content.Intent
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
import com.fcpunlimited.ubersport.struct.game.*
import com.fcpunlimited.ubersport.utils.Constants.DATE_FORMAT
import com.fcpunlimited.ubersport.utils.Constants.DATE_HOUR_FORMAT
import com.fcpunlimited.ubersport.utils.getSportIconByName
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.adapters.holders.*
import com.fcpunlimited.ubersport.view.main.create_game.CreateGameActivity
import com.fcpunlimited.ubersport.view.main.create_game.CreateGameFragment
import com.fcpunlimited.ubersport.view.main.create_game.SetupGameActivity
import com.fcpunlimited.ubersport.view.main.search.SearchFragment
import com.fcpunlimited.ubersport.view.main.search.description.DescriptionFragment
import com.fcpunlimited.ubersport.view.main.search.dialog.SportsFilterDialogFragment
import com.squareup.picasso.Picasso
import org.jetbrains.anko.image
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class CustomAdapter : BaseListAdapter(), LifecycleObserver {

    private var lifecycleOwner: LifecycleOwner? = null

    private var sportIds: Set<String>? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun addLifecycleOwner(owner: LifecycleOwner) {
        this.lifecycleOwner = owner
        if (lifecycleOwner is SportsFilterDialogFragment) {
            sportIds = (lifecycleOwner as SportsFilterDialogFragment).getFilteredSports()!!
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeLifecycleOwner() {
        lifecycleOwner = null
        sportIds = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context

        return when (viewType) {
            R.layout.choose_sport_item -> ChooseSportViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.search_item -> SearchEventViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.description_participant_item -> DescriptionParticipantsViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.sport_filter_item -> SportsFilterViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.active_game_item -> ActiveGameViewHolder(inflateByViewType(context, viewType, parent))
            R.layout.game_history_item -> GameHistoryViewHolder(inflateByViewType(context, viewType, parent))
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is ChooseSportViewHolder -> bindChooseSportView(holder, position, items, context)
            is SearchEventViewHolder -> bindSearchView(holder, position, items, context)
            is DescriptionParticipantsViewHolder -> bindDescriptionParticipantsView(holder, position, items, context)
            is SportsFilterViewHolder -> bindSportFilterView(holder, position, items, context)
            is ActiveGameViewHolder -> bindActiveGamesViewHolder(holder, position, items, context)
            is GameHistoryViewHolder -> bindGamesHistoryViewHolder(holder, position, items, context)
        }
    }

    private fun bindGamesHistoryViewHolder(holder: GameHistoryViewHolder, position: Int, items: ArrayList<IListItem>, context: Context?) {
        val game = (items[position] as GamesHistoryDto).game
        holder.apply {
            tvGameDate.text = SimpleDateFormat(DATE_FORMAT, Locale.ROOT).format(game.dateStart()
                    ?: 0.0)
            tvGameAuthor.text = game.author()?.nickname() ?: "null nickname"
            tvEventName.text = game.name()
        }
    }

    private fun bindActiveGamesViewHolder(holder: ActiveGameViewHolder, position: Int, items: ArrayList<IListItem>, context: Context) {
        val game = (items[position] as ActiveGamesDto).game
        holder.apply {
            tvGameDate.text = SimpleDateFormat(DATE_FORMAT, Locale.ROOT).format(game.dateStart()
                    ?: 0.0)
            tvGameAuthor.text = game.author()?.nickname() ?: "null nickname"
            tvEventName.text = game.name()
        }
    }

    private fun bindSportFilterView(holder: SportsFilterViewHolder, position: Int,
                                    items: ArrayList<IListItem>, context: Context) {
        val sport = items[position] as SportFilterDto
        holder.apply {
            tvSportCheckBox.text = sport.game.name()
//            tvSportCheckBox.isChecked = (sportIds!!.contains(sport.game.id()))
            tvSportCheckBox.isChecked = (false)
            tvSportCheckBox.setOnClickListener {
                sport.game.id()?.let { id ->
                    //                    if (tvSportCheckBox.isChecked) {
//                        tvSportCheckBox.isChecked = false
//                        (lifecycleOwner as SportsFilterDialogFragment).removeFilterSportId(id)
//                    } else {
//                        tvSportCheckBox.isChecked = true
//
//                        (lifecycleOwner as SportsFilterDialogFragment).addFilterSport(id)
//
//                    }
                }
            }
        }
    }

    private fun bindDescriptionParticipantsView(holder: DescriptionParticipantsViewHolder,
                                                position: Int, items: ArrayList<IListItem>,
                                                context: Context) {
        val participant = (items[position] as GameParticipantsDto).participant
        holder.apply {
            if (participant.__typename() == "STUB") {
                loadImage(R.drawable.add, ivDescriptionParticipant)
                tvParticipantName.text = "Add player"
                return
            }
            if ((lifecycleOwner as DescriptionFragment).isGameOwner) {
                excludeParticipantLayout.visibility = View.VISIBLE
                excludeParticipantLayout.setOnClickListener { context.toast("exclude participant ${participant.nickname()}") }
            }
            participantLayout.setOnClickListener { participant.nickname()?.let { it1 -> context.toast(it1) } }
            tvParticipantName.text = participant.nickname()
            loadImage(R.drawable.avatar, ivDescriptionParticipant)
        }
    }

    private fun bindChooseSportView(holder: ChooseSportViewHolder, position: Int,
                                    items: ArrayList<IListItem>, context: Context) {
        val sport = items[position] as SportDto

        holder.apply {
            tvEventName.text = sport.game.name()
            itemView.setOnClickListener {
                context.toast(sport.game.name()!!)
                context.startActivity(Intent(context,
                        CreateGameFragment::class.java))
            }
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
                        .format(dateStart()?.toLong())
                location()?.let { tvEventAddress.text = it.address() }
                author()?.let {
                    tvAuthor.text = it.nickname()
                    tvSubtitle.text = "${it.nickname()}"
                }
                tvGameTime.text = SimpleDateFormat(DATE_HOUR_FORMAT, Locale.ROOT).format(dateEnd()?.minus(dateStart()!!)
                        ?: 0)
//                participants()?.let {
//                    val participantsLimit = participantsLimit()?.toInt() ?: 0
//                    progressBar.max = participantsLimit
//                    progressBar.progress = it.size
//                    tvParticipantsCount.text = "${it.size}/$participantsLimit"
//                }
                sport()?.let {
                    ivSportIcon.image = ContextCompat.getDrawable(context, getSportIconByName(it.name()!!))
                    val participantsLimit = it.participantCount()?.toInt() ?: 0
                    progressBar.max = participantsLimit
                    progressBar.progress = participants()?.size ?: 0
                    tvParticipantsCount.text = "${participants()?.size}/$participantsLimit"
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
                .centerCrop()
                .fit()
                .error(R.color.red_light)
                .into(imageView)
    }
}