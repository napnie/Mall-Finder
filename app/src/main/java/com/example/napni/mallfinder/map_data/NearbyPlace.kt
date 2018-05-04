package com.example.napni.mallfinder.map_data

import android.content.res.Resources
import android.location.Geocoder
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.napni.mallfinder.R
import com.google.android.gms.maps.GoogleMap
import java.util.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class NearbyPlace : AsyncTask<Objects, String, String>() {

    val PROXIMITY_RADIUS = 300

    lateinit var nearby_data : String
    lateinit var mMap : GoogleMap
    lateinit var url : String
    var zoom = 15f

    override fun doInBackground(vararg p0: Objects?): String {

        nearby_data = DataDownloader().readUrl(url)
        return nearby_data
    }

    fun setData(mMap : GoogleMap, url : String, zoom : Float) {
        this.mMap = mMap
        this.url = url
        this.zoom = zoom
    }

    override fun onPostExecute(result: String?) {
        val nearbyPlaceList: List<HashMap<String, String>>
        val parser = DataParser()
        nearbyPlaceList = parser.parse(result.toString() )
        Log.d("nearbyplacesdata", "called parse method")
        showNearbyPlaces(nearbyPlaceList)
    }

    private fun showNearbyPlaces(nearbyPlaceList: List<HashMap<String, String>>) {
        for (i in nearbyPlaceList.indices) {
            val markerOptions = MarkerOptions()
            val googlePlace = nearbyPlaceList[i]

            val placeName = googlePlace["place_name"]
            val vicinity = googlePlace["vicinity"]
            val lat = java.lang.Double.parseDouble(googlePlace["lat"])
            val lng = java.lang.Double.parseDouble(googlePlace["lng"])

            val latLng = LatLng(lat, lng)
            markerOptions.position(latLng)
            markerOptions.title("$placeName : $vicinity")
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

            mMap.addMarker(markerOptions)
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom))
        }
    }

    fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String, api_key : String): String {

        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("sensor=true")
        googlePlaceUrl.append("&key=$api_key")

        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString())

        return googlePlaceUrl.toString()
    }
}