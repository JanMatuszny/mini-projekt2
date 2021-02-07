package com.jmatuszny.projekt1

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */

private lateinit var views: RemoteViews
private var imageIdx: Int = 0

class MediaAppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == context?.getString(R.string.action1)) {
            Toast.makeText(context, "Action1", Toast.LENGTH_LONG).show()
        }
        else if (intent?.action == context?.getString(R.string.imageAction)) {

            val views = RemoteViews(context!!.packageName, R.layout.media_app_widget)

            val images = arrayListOf(R.drawable.android, R.drawable.blueandroid)
            views.setImageViewResource(R.id.imageView, images[++imageIdx % images.size])
            val manager = AppWidgetManager.getInstance(context)
            manager.updateAppWidget(
                    manager.getAppWidgetIds(ComponentName(context, MediaAppWidget::class.java)),
                    views
            )
        }

            super.onReceive(context, intent) // to na koniec
        }
    }

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    views = RemoteViews(context.packageName, R.layout.media_app_widget)
    views.setTextViewText(R.id.widget_tv, widgetText)
    views.setImageViewResource(R.id.imageView, R.drawable.android)

    // WWW
    val intentWWW = Intent(Intent.ACTION_VIEW)
    intentWWW.data = Uri.parse("https://google.com")
    val pendingWWW = PendingIntent.getActivity(
            context,
            0,
            intentWWW,
            PendingIntent.FLAG_UPDATE_CURRENT)

    views.setOnClickPendingIntent(R.id.widget_bt1, pendingWWW)
    // end WWW

    val intentAction = Intent(context.getString(R.string.action1))

    intentAction.component = ComponentName(context, MediaAppWidget::class.java)
    val pendingAction = PendingIntent.getBroadcast(
            context,
            0,
            intentAction,
            PendingIntent.FLAG_UPDATE_CURRENT)

    views.setOnClickPendingIntent(R.id.widget_bt2, pendingAction)

    val intentImage = Intent(context.getString(R.string.imageAction))

    intentImage.component = ComponentName(context, MediaAppWidget::class.java)
    val pendingImage = PendingIntent.getBroadcast(
            context,
            0,
            intentImage,
            PendingIntent.FLAG_UPDATE_CURRENT)

    views.setOnClickPendingIntent(R.id.imageView, pendingImage)


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}