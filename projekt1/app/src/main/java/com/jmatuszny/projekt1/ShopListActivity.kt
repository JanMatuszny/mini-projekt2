package com.jmatuszny.projekt1

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.*
import com.jmatuszny.projekt1.databinding.ActivityShopListBinding
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class ShopListActivity : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        ref = database.getReference("shops")

        val list = arrayListOf<Shop>()
        val adapter = ShopsAdapter(this, list, ref)

        binding.RecyclerView.adapter = adapter

        val geoClient = LocationServices.getGeofencingClient(this)
        var id = 0

        binding.AddButton.setOnClickListener {
            val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(perms, 0)
            }
            LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    Log.i("location", "Location: ${it.latitude}, ${it.longitude}")

                    var latitude = it.latitude
                    var longitude = it.longitude

                    val shop = Shop(
                        name = binding.NameEditText.text.toString(),
                        description = binding.DescriptionEditText.text.toString(),
                        radius = binding.RadiusEditText.text.toString().toLong(),
                        longitude = longitude,
                        latitude = latitude)
                    CoroutineScope(Dispatchers.IO).launch {
                        ref.push().setValue(shop)
                    }

//                    Toast.makeText(this,
//                        "${binding.NameEditText.text.toString()} has been added",
//                        Toast.LENGTH_SHORT).show()

                    val geo = Geofence.Builder()
                        .setRequestId("Geo${id++}")
                        .setCircularRegion(it.latitude, it.longitude, binding.RadiusEditText.text.toString().toFloat())
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .build()

                    val geoRequest = GeofencingRequest.Builder()
                        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                        .addGeofence(geo)
                        .build()

                    val intent = Intent(this, GeoReceiver::class.java)

                    val geoPendingIntent = PendingIntent.getBroadcast(
                        this,
                        id,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    geoClient.addGeofences(geoRequest, geoPendingIntent)

                }.addOnFailureListener {
                    Log.i("Exception", it.message.toString())
                }
        }

        binding.MapButton.setOnClickListener {
            val mapIntent = Intent(this, MapsActivity::class.java)

            startActivity(mapIntent)
        }
    }
}