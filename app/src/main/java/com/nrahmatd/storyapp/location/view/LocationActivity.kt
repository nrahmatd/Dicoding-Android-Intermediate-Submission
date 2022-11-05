package com.nrahmatd.storyapp.location.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.nrahmatd.storyapp.R
import com.nrahmatd.storyapp.baseview.BaseActivity
import com.nrahmatd.storyapp.databinding.ActivityLocationBinding
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.home.viewmodel.AllStoriesViewModel
import com.nrahmatd.storyapp.home.viewmodel.AllStoriesViewModelFactory
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.toast

class LocationActivity : BaseActivity<ActivityLocationBinding>() {

    private lateinit var allStoriesViewModel: AllStoriesViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        setMapStyle()
        getDeviceLocation()
    }

    companion object {
        const val ALL_STORIES = 1
    }

    override val bindingInflater: (LayoutInflater) -> ActivityLocationBinding
        get() = ActivityLocationBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        initMap()
        initViewModel()
        getAllStories()
        getResponse()
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initViewModel() {
        allStoriesViewModel =
            ViewModelProvider(this, AllStoriesViewModelFactory())[AllStoriesViewModel::class.java]
    }

    override fun statusBarColor(): Int = 0

    /**
     * Get device location and request location permission if still doesn't have any location permission
     */
    private fun getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
                } else {
                    toast(this, getString(R.string.please_activate_location_message))
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.my_map_style
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun markStoriesLocation(storiesLocation: AllStoriesModel) {
        storiesLocation.listStory.forEach { story ->
            if (story.lat != null && story.lon != null) {
                val latLng = LatLng(story.lat, story.lon)

                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(story.name)
                        .snippet("Lat: ${story.lat}, Lon: ${story.lon}")
                )
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getDeviceLocation()
            }
        }

    private fun getAllStories() = allStoriesViewModel.getAllStories(ALL_STORIES)

    private fun getResponse() {
        allStoriesViewModel.response.observe(this) {
            when (it.code) {
                ResponseHelper.ERROR -> toast(this, it.response as String)
                ALL_STORIES -> {
                    val response = it.response as AllStoriesModel
                    markStoriesLocation(response)
                }
            }
        }
    }
}
