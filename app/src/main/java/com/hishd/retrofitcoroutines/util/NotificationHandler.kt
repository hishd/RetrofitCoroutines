package com.hishd.retrofitcoroutines.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import androidx.core.app.NotificationCompat
import okhttp3.internal.notify

class NotificationHandler(
    private val context: Context,
    private val channelID: String,
    channelName: String,
    channelDescription: String,) {

    private var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel(channelID, channelName, channelDescription)
    }

    private fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                this.description = channelDescription
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun displayNotification(notificationId: Int, title: String, contentText: String, smallIcon: Int, autoCancel: Boolean, priority: Int) {
        val notification = NotificationCompat.Builder(context, channelID).apply {
            this.setContentTitle(title)
            this.setContentText(contentText)
            this.setSmallIcon(smallIcon)
            this.setAutoCancel(autoCancel)
            this.priority = priority
        }.build()
        notificationManager.notify(notificationId, notification)
    }
}