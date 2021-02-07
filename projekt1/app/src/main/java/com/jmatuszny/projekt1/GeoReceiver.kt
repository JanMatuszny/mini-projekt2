package com.jmatuszny.projekt1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeoReceiver : BroadcastReceiver() {
    private var id = 0
    override fun onReceive(context: Context, intent: Intent) {

        val event = GeofencingEvent.fromIntent(intent)
        for (geo in event.triggeringGeofences) {
//            Toast.makeText(context, "${geo.requestId}", Toast.LENGTH_SHORT).show()
            var text: String

            if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                text = "You are near your favourite shop"
            } else {
                text = "You left your favourite shop"
            }

            createChannel(context)
            val intentShopList = Intent(context, ShopListActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                id,
                intentShopList,
                PendingIntent.FLAG_ONE_SHOT)
            val notification = NotificationCompat.Builder(context, "channelShop")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notificaiton")
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(id++, notification)
        }
    }

    private fun createChannel(context: Context) {
        val notificationChannel = NotificationChannel(
            "channelShop",
            "addShop",
            NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}