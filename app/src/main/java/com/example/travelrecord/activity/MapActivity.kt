package com.example.travelrecord.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelrecord.R
import com.example.travelrecord.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 서울시청 위치 (위도 37.5665, 경도 126.9780)
        val seoulCityHall = LatLng(37.5665, 126.9780)
        
        // 마커 추가
        mMap.addMarker(MarkerOptions()
            .position(seoulCityHall)
            .title("서울시청"))
        
        // 카메라 이동 및 줌 레벨 15 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoulCityHall, 15f))
    }
}