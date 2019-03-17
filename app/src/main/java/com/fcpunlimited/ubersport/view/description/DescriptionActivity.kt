package com.fcpunlimited.ubersport.view.description

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.container_game_header.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class DescriptionActivity : BaseMvpActivity(), OnMapReadyCallback {

    private val descriptionActivityParcel: DescriptionActivityParcel by inject()
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        scrollView.fullScroll(ScrollView.FOCUS_UP)
        initMap()
        initParticipants()
    }

    private fun initParticipants() {
        iv_participant_1?.visibility = View.VISIBLE
        iv_participant_2?.visibility = View.VISIBLE
        iv_participant_3?.visibility = View.VISIBLE
        iv_participant_4?.visibility = View.VISIBLE
        iv_participant_5?.visibility = View.VISIBLE
        iv_participant_plus?.visibility = View.VISIBLE

        val game = descriptionActivityParcel.game

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
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isScrollGesturesEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = false
        val location = descriptionActivityParcel.game?.location()
        location?.latitude()?.let { lat ->
            location.longitude().let { lng ->
                val latlng = LatLng(lat, lng)
                mMap.addMarker(MarkerOptions().position(latlng).title(location.address()))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11f))
            }
        }
    }
}
