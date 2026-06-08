package com.test.siana.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmActionReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (
            intent.action ==
            "STOP_ALARM"
        ) {

            val serviceIntent =
                Intent(
                    context,
                    MonitoringService::class.java
                )

            serviceIntent.action =
                "STOP_ALARM"

            context.startService(
                serviceIntent
            )
        }
    }
}