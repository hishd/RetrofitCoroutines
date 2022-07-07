package com.hishd.retrofitcoroutines.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class NotificationHandler(
    private val context: Context,
    private val channelID: String,
    channelName: String,
    channelDescription: String,
) {

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

    fun displayNotification(
        notificationId: Int,
        title: String,
        contentText: String,
        smallIcon: Int,
        autoCancel: Boolean = true,
        priority: Int = NotificationCompat.PRIORITY_HIGH
    ) {
        val notification = NotificationCompat.Builder(context, channelID).apply {
            this.setContentTitle(title)
            this.setContentText(contentText)
            this.setSmallIcon(smallIcon)
            this.setAutoCancel(autoCancel)
            this.priority = priority
        }.build()
        notificationManager.notify(notificationId, notification)
    }

    //TODO: This method requires testing
    //Notification with a Tap Action
    fun displayNotification(
        notificationId: Int,
        title: String,
        contentText: String,
        smallIcon: Int,
        autoCancel: Boolean = true,
        priority: Int = NotificationCompat.PRIORITY_HIGH,
        pendingIntent: Intent
    ) {
        val intent =
            PendingIntent.getActivity(context, 0, pendingIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, channelID).apply {
            this.setContentTitle(title)
            this.setContentText(contentText)
            this.setSmallIcon(smallIcon)
            this.setAutoCancel(autoCancel)
            this.priority = priority
            this.setContentIntent(intent)
        }.build()
        notificationManager.notify(notificationId, notification)
    }

    //TODO: This method requires testing
    //Notification with direct reply action
    fun displayNotification(
        notificationId: Int,
        title: String,
        contentText: String,
        smallIcon: Int,
        autoCancel: Boolean = true,
        priority: Int = NotificationCompat.PRIORITY_HIGH,
        keyReply: String,
        pendingIntent: Intent
    ) {
        val intent =
            PendingIntent.getActivity(context, 0, pendingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val remoteInput = RemoteInput.Builder(keyReply).run {
            setLabel("Insert Text")
            build()
        }

        val replyAction = NotificationCompat.Action.Builder(0, "Reply", intent).addRemoteInput(remoteInput).build()

        val notification = NotificationCompat.Builder(context, channelID).apply {
            this.setContentTitle(title)
            this.setContentText(contentText)
            this.setSmallIcon(smallIcon)
            this.setAutoCancel(autoCancel)
            this.priority = priority
            this.addAction(replyAction)
        }.build()
        notificationManager.notify(notificationId, notification)
    }

    /*
        Write this code in the Pending Intent Activity to set the status of the notification

        private fun receiveInput() {
            val KEY_REPLY = "key_reply"
            val intent = this.intent
            val remoteInput = RemoteInput.getResultsFromIntent(intent)
            if (remoteInput != null) {
                val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
                result_text_view.text = inputString

                val channelID = "com.anushka.notificationdemo.channel1"
                val notificationId = 45

                val repliedNotification = NotificationCompat.Builder(this,channelID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentText("Your reply received")
                    .build()
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(notificationId,repliedNotification)
            }
        }

     */

    //TODO: This method requires testing
    //Notification with buttons
    fun displayNotification(
        notificationId: Int,
        title: String,
        contentText: String,
        smallIcon: Int,
        autoCancel: Boolean = true,
        priority: Int = NotificationCompat.PRIORITY_HIGH,
        actionTitle1: String?,
        action1SmallIcon: Int = 0,
        action1PendingIntent: Intent?,
        actionTitle2: String?,
        action2SmallIcon: Int = 0,
        action2PendingIntent: Intent?
    ) {
        val intent1: PendingIntent?
        var action1: NotificationCompat.Action? = null

        val intent2: PendingIntent?
        var action2: NotificationCompat.Action? = null

        if(actionTitle1 != null && action1PendingIntent != null) {
            intent1 = PendingIntent.getActivity(
                context,
                0,
                action1PendingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            action1 = NotificationCompat.Action.Builder(action1SmallIcon, actionTitle1, intent1).build()
        }

        if(actionTitle2 != null && action2PendingIntent != null) {
            intent2 = PendingIntent.getActivity(
                context,
                0,
                action2PendingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            action2 = NotificationCompat.Action.Builder(action2SmallIcon, actionTitle2, intent2).build()
        }

        val notification = NotificationCompat.Builder(context, channelID).apply {
            this.setContentTitle(title)
            this.setContentText(contentText)
            this.setSmallIcon(smallIcon)
            this.setAutoCancel(autoCancel)
            this.priority = priority
            action1?.let {
                this.addAction(it)
            }
            action2?.let {
                this.addAction(it)
            }
        }.build()
        notificationManager.notify(notificationId, notification)
    }
}