package com.test.siana.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.siana.R

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onMessageReceived(
        message: RemoteMessage
    ) {

        showNotification(
            message.notification?.title ?: "SIANA",
            message.notification?.body ?: ""
        )
    }

    private fun showNotification(
        title: String,
        body: String
    ) {

        val manager =
            getSystemService(
                NotificationManager::class.java
            )

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =
                NotificationChannel(
                    "siana_alert",
                    "SIANA Alert",
                    NotificationManager.IMPORTANCE_HIGH
                )

            manager.createNotificationChannel(
                channel
            )
        }

        val notification =
            NotificationCompat.Builder(
                this,
                "siana_alert"
            )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .build()

        manager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }
}