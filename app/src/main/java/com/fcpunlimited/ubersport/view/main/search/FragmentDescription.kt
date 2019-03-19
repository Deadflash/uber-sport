package com.fcpunlimited.ubersport.view.main.search


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.utils.SportType
import com.fcpunlimited.ubersport.utils.layout.FragmentTags.DESCRIPTION_FRAGMENT_TAG
import com.fcpunlimited.ubersport.view.BaseMvpFragment
import com.fcpunlimited.ubersport.view.main.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.container_game_header.*
import kotlinx.android.synthetic.main.fragment_description.*
import org.jetbrains.anko.image
import java.text.SimpleDateFormat
import java.util.*

class FragmentDescription : BaseMvpFragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var gameConsumer: IGameShare.IGameConsumer? = null
    private var game: GamesQuery.Game? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            gameConsumer = context
            game = gameConsumer?.consumeGame()
        }
    }

    override fun onDetach() {
        super.onDetach()
        gameConsumer = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollView.fullScroll(ScrollView.FOCUS_UP)
        game?.let {
            setupView(it)
            initMap()
        }
    }

    private fun setupView(game: GamesQuery.Game) {

        val participantsAvatars = arrayOf(iv_participant_1, iv_participant_2,
                iv_participant_3, iv_participant_4, iv_participant_5, iv_participant_plus)

        game.author()?.let {
            tv_author.text = it.nickname()
            tv_author_subtitle.text = "${it.firstName()} ${it.lastName()}"

        }
        tv_game_title.text = game.name()
        tv_game_description.text = game.description()
        game.length()?.let { tv_game_time.text = SimpleDateFormat("HH:mm", Locale.ROOT).format(it) }
        tv_address.text = game.location()?.address()
        tv_date.text = SimpleDateFormat("EEE dd-MMM h:mm", Locale.ROOT)
                .format(game.dateStart().toLong())
        game.participants()?.let {
            val participantsLimit = game.participantsLimit()?.toInt() ?: 0
            progressBar.max = participantsLimit
            progressBar.progress = it.size
            tv_participants_count.text = "${it.size}/$participantsLimit"

            for (position in 0 until it.size){
                val participantAvatar = participantsAvatars[position]
                participantAvatar.visibility = View.VISIBLE
                Picasso.get().load(R.drawable.avatar)
                        .fit()
                        .error(R.color.colorAccent)
                        .into(participantAvatar)
            }
        }
        game.sport().let {
            when (it?.name()) {
                SportType.Football.name -> {
                    iv_sport_icon.image = context?.let { context -> ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football) }
                }
                SportType.Basketball.name -> {
                    iv_sport_icon.image = context?.let { context -> ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_baseball) }

                }
                SportType.Bicycle.name -> {
                    iv_sport_icon.image = context?.let { context -> ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football) }

                }
                SportType.Paintball.name -> {
                    iv_sport_icon.image = context?.let { context -> ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_football) }

                }
                SportType.Tennis.name -> {
                    iv_sport_icon.image = context?.let { context -> ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_tennisball) }

                }
                SportType.Volleyball.name -> {
                    iv_sport_icon.image = context?.let { context -> ContextCompat.getDrawable(context, R.drawable.ic__ionicons_svg_md_tennisball) }
                }
                else -> iv_sport_icon.image = context?.let { ContextCompat.getDrawable(it, R.drawable.ic__ionicons_svg_md_football) }
            }
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isScrollGesturesEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = false
        val location = game?.location()
        location?.latitude()?.let { lat ->
            location.longitude().let { lng ->
                val latlng = LatLng(lat, lng)
                mMap.addMarker(MarkerOptions().position(latlng).title(location.address()))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11f))
            }
        }
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_description

    override fun getFragmentTag(): String = DESCRIPTION_FRAGMENT_TAG

    override fun getFragmentMenu(): Int? = null
}
