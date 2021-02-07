package com.jmatuszny.projekt1

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*
import java.util.prefs.Preferences
import kotlin.collections.HashMap
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var database = FirebaseDatabase.getInstance()
    lateinit var ref: DatabaseReference

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        ref = database.getReference("shops")
        ref.orderByChild("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.value == null) {
                    return
                }

                val response = snapshot.value as HashMap<String, HashMap<String, Objects>>

                response.forEach {
                    var title = it.value["name"] as String
                    var latitude = it.value["latitude"] as Double
                    var longitude = it.value["longitude"] as Double

                    val marker = MarkerOptions()
                        .position(LatLng(latitude, longitude))
                        .title(title)

                    mMap.addMarker(marker)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MapsActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })

        val geoClient = LocationServices.getGeofencingClient(this)
        var id = 0

        bt_place.setOnClickListener {
            LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener {
                Log.i("location", "Location: ${it.latitude}, ${it.longitude}")

                val latlng = LatLng(it.latitude, it.longitude)
                val marker = MarkerOptions()
                    .position(latlng)
                    .title(et_place.text.toString())

                mMap.addMarker(marker)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))

                val geo = Geofence.Builder()
                        .setRequestId("Geo${id++}")
                        .setCircularRegion(it.latitude, it.longitude, 100F)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .build()

                val geoRequest = GeofencingRequest.Builder()
                        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                        .addGeofence(geo)
                        .build()

                val intent = Intent(this, GeoReceiver::class.java)
                intent.putExtra("name", et_place.text.toString())

                val geoPendingIntent = PendingIntent.getBroadcast(
                        this,
                        id,
                        Intent(this, GeoReceiver::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )

                geoClient.addGeofences(geoRequest, geoPendingIntent)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(perms, 0)
        }
        mMap.isMyLocationEnabled = true

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(52.2297, 21.0122), 10F)) // centrum warszawy
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    }
}