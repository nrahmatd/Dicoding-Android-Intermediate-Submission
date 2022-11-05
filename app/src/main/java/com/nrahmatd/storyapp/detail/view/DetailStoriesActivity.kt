package com.nrahmatd.storyapp.detail.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
import com.nrahmatd.storyapp.databinding.ActivityDetailStoriesBinding
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.utils.parseDate

class DetailStoriesActivity : BaseActivity<ActivityDetailStoriesBinding>(), OnMapReadyCallback {

    private lateinit var stories: AllStoriesModel.Story

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override val bindingInflater: (LayoutInflater) -> ActivityDetailStoriesBinding
        get() = ActivityDetailStoriesBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        /** Wait until all resource is already loaded */
        supportPostponeEnterTransition()
        initExtras()
        initView()
    }

    override fun statusBarColor(): Int = 0

    private fun initExtras() {
        stories = intent.getSerializableExtra("stories") as AllStoriesModel.Story
    }

    private fun initView() {
        binding.apply {
            tvStoryName.text = stories.name
            tvStoryDescription.text = stories.description
            tvStoryDate.text = parseDate(stories.createdAt)
            /** Parse image to ImageView
             * Using listener for make sure the enter transition only called when loading completed
             * */
            Glide
                .with(this@DetailStoriesActivity)
                .load(stories.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Continue enter animation after image loaded
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(ivStoryImage)

            if (stories.lat == null && stories.lon == null) {
                mapDetail.visibility = View.GONE
                ivNotFound.visibility = View.VISIBLE
            } else {
                initMap()
            }
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_detail) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

    private fun markStoryLocation() {
        val storyPlace = LatLng(stories.lat ?: 0.0, stories.lon ?: 0.0)

        mMap.addMarker(
            MarkerOptions()
                .position(storyPlace)
                .title(stories.name)
                .snippet("Lat: ${stories.lat}, Lon: ${stories.lon}")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(storyPlace, 15f))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        setMapStyle()
        markStoryLocation()
    }
}
