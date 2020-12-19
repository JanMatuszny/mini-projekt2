package com.jmatuszny.projekt2

import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {

    private var mBinder: MyBinder
    private var id = 0

    init {
        mBinder = MyBinder()
    }

    inner class MyBinder: Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        if (intent != null) {
            sendNotification(intent)
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    private fun sendNotification(intent: Intent) {
        val productId = intent.getIntExtra("id", -1)
        val editProduct = Intent(getString(R.string.addProduct))

        editProduct.putExtra("id", productId)

        editProduct.component = ComponentName(
                getString(R.string.projekt1Package),
                getString(R.string.editProductActivity))

        val pendingIntent = PendingIntent.getActivity(
                this,
                id,
                editProduct,
                PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(this, getString(R.string.channelID))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Dodano produkt")
                .setContentText(productId.toString())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat.from(this).notify(id++, notification)
    }
}