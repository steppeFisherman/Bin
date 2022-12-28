package com.example.bin.ui

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.example.bin.R
import com.example.bin.ui.model.DataUi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var geoCoder: Geocoder
    private lateinit var bin: DataUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true

        setContentView(R.layout.activity_maps)

        geoCoder = Geocoder(this, Locale.getDefault())
        val bundle = intent.extras?.getParcelable<DataUi>("bin")
        if (bundle != null) bin = bundle

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        val currentLatLong = LatLng(bin.latitude.toDouble(), bin.longitude.toDouble())
        placeMarkerOnMap(currentLatLong)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 5f))
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val locationAddress = geoCoder
            .getFromLocation(currentLatLong.latitude, currentLatLong.longitude, 2)

        if (locationAddress.isNotEmpty() && locationAddress.size > 1) {
            val address = locationAddress[1].getAddressLine(0)
            val markerOptions = MarkerOptions().position(currentLatLong)
            markerOptions.title(address)
            mMap.addMarker(markerOptions)
        }
    }

    override fun onMarkerClick(p0: Marker) = false
}

