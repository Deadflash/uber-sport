package com.fcpunlimited.ubersport.view.description

import android.os.Bundle
import android.view.View
import androidx.transition.Visibility
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
import org.jetbrains.anko.sdk25.coroutines.onClick

class DescriptionActivity : BaseMvpActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        initMap()
        initParticipants()
//        initViewPager()
//        iv_close.onClick { finish() }
    }

    private fun initParticipants(){
        iv_participant_1.visibility = View.VISIBLE
        iv_participant_2.visibility = View.VISIBLE
        iv_participant_3.visibility = View.VISIBLE
        iv_participant_4.visibility = View.VISIBLE
        iv_participant_5.visibility = View.VISIBLE
        iv_participant_plus.visibility = View.VISIBLE
    }

//    private fun initViewPager() {
//        view_pager.adapter = DescriptionPagerAdapter(supportFragmentManager)
//        tab_layout.setupWithViewPager(view_pager, true)
//    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
