package com.test.siana.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.test.siana.R
import com.test.siana.data.model.DisasterLevel
import com.test.siana.utils.ThresholdManager
import com.test.siana.data.model.ThresholdConfig
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.VibratorManager
import android.os.Vibrator
import android.app.PendingIntent
import com.test.siana.data.model.NotificationModel




class MonitoringService : Service() {

    private lateinit var databaseRef: DatabaseReference

    private var valueEventListener: ValueEventListener? = null


    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()

        val foregroundNotification =
            NotificationCompat.Builder(
                this,
                "monitoring_channel"
            )
                .setContentTitle("SIANA Monitoring")
                .setContentText("Memantau kondisi sensor")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()

        startForeground(
            1,
            foregroundNotification
        )

        loadUserDevice()
    }

    private fun loadUserDevice() {

        val uid =
            FirebaseAuth.getInstance()
                .currentUser?.uid
                ?: return

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                val deviceId =
                    document.getString("apiKey")
                        ?: return@addOnSuccessListener


                loadThreshold(deviceId)
                startMonitoring(deviceId)
            }
    }

    private fun startMonitoring(
        deviceId: String
    ) {

        databaseRef =
            FirebaseDatabase.getInstance()
                .getReference(
                    "devices/$deviceId/data"
                )

        valueEventListener =
            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    val waterLevel =
                        snapshot.child("water_level")
                            .getValue(Double::class.java)
                            ?: 0.0

                    val mq135 =
                        snapshot.child("mq135")
                            .getValue(Int::class.java)
                            ?: 0

                    val vibration =
                        snapshot.child("vibration")
                            .getValue(Int::class.java)
                            ?: 0

                    val temperature =
                        snapshot.child("temperature")
                            .getValue(Int::class.java)
                            ?: 0

                    checkFlood(waterLevel)
                    checkQuake(vibration)
                    checkFire(temperature)


                    // nanti bisa ditambah:
                    // checkAirQuality(mq135)
                    // checkEarthquake(vibration)
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {
                }
            }

        databaseRef.addValueEventListener(
            valueEventListener!!
        )
    }

    private var lastFloodStatus =
        DisasterLevel.AMAN

    private var lastQuakeStatus =
        DisasterLevel.AMAN

    private var lastFireStatus =
        DisasterLevel.AMAN


    private fun getFloodStatus(
        waterLevel: Double
    ): DisasterLevel {

        val threshold =
            ThresholdManager.config

        return when {

            waterLevel >= threshold.water_max ->
                DisasterLevel.BAHAYA

            waterLevel >= 50 ->
                DisasterLevel.WASPADA

            else ->
                DisasterLevel.AMAN
        }
    }

    private fun getQuakeStatus(
        vibration: Int
    ): DisasterLevel {

        val threshold =
            ThresholdManager.config

        return when {

            vibration >= threshold.vibration_max ->
                DisasterLevel.BAHAYA

            vibration >= 25 ->
                DisasterLevel.WASPADA

            else ->
                DisasterLevel.AMAN
        }
    }

    private fun getFireStatus(
        temperature: Int
    ): DisasterLevel {

        val threshold =
            ThresholdManager.config

        return when {

            temperature >= threshold.temp_max ->
                DisasterLevel.BAHAYA

            temperature >= 40 ->
                DisasterLevel.WASPADA

            else ->
                DisasterLevel.AMAN
        }
    }

    private fun checkFlood(
        waterLevel: Double
    ) {

        val currentStatus =
            getFloodStatus(waterLevel)

        if (
            currentStatus ==
            lastFloodStatus
        ) {
            return
        }

        lastFloodStatus =
            currentStatus

        when(currentStatus) {

            DisasterLevel.WASPADA -> {

                showAlertNotification(
                    "⚠️ Waspada Banjir",
                    "Level air mencapai $waterLevel cm"
                )

                saveNotification(
                    "⚠️ Waspada Banjir",
                    "Level air mencapai $waterLevel cm",
                    "warning"
                )
            }

            DisasterLevel.BAHAYA -> {

                showAlertNotification(
                    "🚨 Bahaya Banjir",
                    "Level air mencapai $waterLevel cm",
                    true
                )

                saveNotification(
                    "🚨 Bahaya Banjir",
                    "Level air mencapai $waterLevel cm",
                    "danger"
                )

                playAlarm()
                vibratePhone()
            }

            DisasterLevel.AMAN -> {}
        }
    }

    private fun checkQuake(
        vibration: Int
    ) {

        val mmi = vibration/10

        val currentStatus =
            getQuakeStatus(vibration)

        if (
            currentStatus ==
            lastQuakeStatus
        ) {
            return
        }

        lastQuakeStatus =
            currentStatus

        when(currentStatus) {

            DisasterLevel.WASPADA -> {

                showAlertNotification(
                    "⚠️ Waspada Gempa",
                    "Terdeteksi getaran mencapai $mmi MMI"
                )

                saveNotification(
                    "⚠️ Waspada Gempa",
                    "Terdeteksi getaran mencapai $mmi MMI",
                    "warning"
                )
            }

            DisasterLevel.BAHAYA -> {

                showAlertNotification(
                    "🚨 Bahaya Gempa",
                    "Level getaran mencapai $mmi MMI",
                    true
                )

                saveNotification(
                    "🚨 Bahaya Gempa",
                    "Level getaran mencapai $mmi MMI",
                    "danger"
                )

                playAlarm()
                vibratePhone()
            }

            DisasterLevel.AMAN -> {}
        }
    }

    private fun checkFire(
        temperature: Int
    ) {

        val currentStatus =
            getFireStatus(temperature)

        if (
            currentStatus ==
            lastFireStatus
        ) {
            return
        }

        lastFireStatus =
            currentStatus

        when(currentStatus) {

            DisasterLevel.WASPADA -> {

                showAlertNotification(
                    "⚠️ Waspada Kebakaran",
                    "Terdeteksi kenaikan suhu mencapai $temperature °C"
                )

                saveNotification(
                    "⚠️ Waspada Kebakaran",
                    "Terdeteksi kenaikan suhu mencapai $temperature °C",
                    "warning"
                )
            }

            DisasterLevel.BAHAYA -> {

                showAlertNotification(
                    "🚨 Bahaya Kebakaran",
                    "Level suhu mencapai $temperature °C",
                    true
                )

                saveNotification(
                    "🚨 Bahaya Kebakaran",
                    "Level suhu mencapai $temperature °C",
                    "danger"
                )


                playAlarm()
                vibratePhone()
            }

            DisasterLevel.AMAN -> {}
        }
    }

    private fun showAlertNotification(
        title: String,
        message: String,
        showStopButton: Boolean = false
    ) {

        val stopIntent =
            Intent(
                this,
                AlarmActionReceiver::class.java
            ).apply {

                action = ACTION_STOP_ALARM
            }

        val stopPendingIntent =
            PendingIntent.getBroadcast(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val builder =
            NotificationCompat.Builder(
                this,
                "monitoring_channel"
            )
                .setSmallIcon(
                    R.mipmap.ic_launcher
                )
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )

        if (showStopButton) {

            builder.addAction(
                0,
                "Matikan Alarm",
                stopPendingIntent
            )
        }

        val manager =
            getSystemService(
                NotificationManager::class.java
            )

        manager.notify(
            System.currentTimeMillis().toInt(),
            builder.build()
        )
    }

    private fun createNotificationChannels() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val monitoringChannel =
                NotificationChannel(
                    "monitoring_channel",
                    "SIANA Monitoring",
                    NotificationManager.IMPORTANCE_LOW
                )

            val alertChannel =
                NotificationChannel(
                    "alert_channel",
                    "SIANA Alerts",
                    NotificationManager.IMPORTANCE_HIGH
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                monitoringChannel
            )

            manager.createNotificationChannel(
                alertChannel
            )
        }
    }

    private fun loadThreshold(
        deviceId: String
    ) {

        FirebaseDatabase.getInstance()
            .getReference(
                "devices/$deviceId/threshold"
            )
            .addValueEventListener(

                object : ValueEventListener {

                    override fun onDataChange(
                        snapshot: DataSnapshot
                    ) {

                        val threshold =
                            snapshot.getValue(
                                ThresholdConfig::class.java
                            )

                        if (threshold != null) {

                            ThresholdManager.config =
                                threshold
                        }
                    }

                    override fun onCancelled(
                        error: DatabaseError
                    ) {}
                }
            )
    }

    private var mediaPlayer: MediaPlayer? = null

    private fun playAlarm() {

        if (mediaPlayer?.isPlaying == true)
            return

        mediaPlayer =
            MediaPlayer.create(
                this,
                R.raw.alarm
            )

        mediaPlayer?.isLooping = true

        mediaPlayer?.start()
    }

    private fun stopAlarm() {

        mediaPlayer?.stop()

        mediaPlayer?.release()

        mediaPlayer = null
    }

    companion object {

        const val ACTION_STOP_ALARM =
            "STOP_ALARM"

    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        if (
            intent?.action ==
            ACTION_STOP_ALARM
        ) {

            stopAlarm()
        }

        return START_STICKY
    }

    private fun vibratePhone() {

        if (android.os.Build.VERSION.SDK_INT >= 31) {

            val vibrator =
                getSystemService(
                    VibratorManager::class.java
                ).defaultVibrator

            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    2000,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )

        } else {

            val vibrator =
                getSystemService(
                    VIBRATOR_SERVICE
                ) as Vibrator

            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    2000,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    private fun saveNotification(
        title: String,
        description: String,
        level: String
    ) {

        val uid =
            FirebaseAuth.getInstance()
                .currentUser?.uid
                ?: return

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("notifications")
            .add(

                NotificationModel(
                    title = title,
                    description = description,
                    level = level,
                    timestamp =
                        System.currentTimeMillis()
                )
            )
    }

    override fun onDestroy() {

        super.onDestroy()

        valueEventListener?.let {

            if (::databaseRef.isInitialized) {

                databaseRef.removeEventListener(
                    it
                )
            }
        }
    }

    override fun onBind(
        intent: Intent?
    ): IBinder? = null
}