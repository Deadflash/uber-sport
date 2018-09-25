package com.fcpunlimited.ubersport.view.description

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fcpunlimited.ubersport.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_description.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DescriptionActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        initMap()
        initViewPager()
        iv_close.onClick { finish() }
    }

    private fun initViewPager() {
        view_pager.adapter = DescriptionViewPagerAdapter(supportFragmentManager)
    }

    private fun initMap(){
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
