package com.jmatuszny.projekt2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startForegroundService

class MyReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {

        createChannel(context)

        val serviceIntent = Intent(context, MyService::class.java)
        val id = intent.getIntExtra("id", -1)

        serviceIntent.putExtra("id", id)

        startForegroundService(context, serviceIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(context: Context) {
        val notificationChannel = NotificationChannel(
                context.getString(R.string.channelID),
                context.getString(R.string.channelName),
                NotificationManager.IMPORTANCE_DEFAULT)

        NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
    }
}