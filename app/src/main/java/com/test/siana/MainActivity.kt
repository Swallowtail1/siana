package com.test.siana

import androidx.core.view.WindowInsetsControllerCompat
import android.graphics.Color
import android.os.Bundle
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.test.siana.ui.navigation.AppNavigation
import com.test.siana.ui.theme.MyApplicationTheme
import com.test.siana.utils.FCMUtils
import androidx.core.app.ActivityCompat
import android.Manifest
import androidx.core.content.ContextCompat
import com.test.siana.service.MonitoringService
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (
            FirebaseAuth.getInstance()
                .currentUser != null
        ) {

            val intent =
                Intent(
                    this,
                    MonitoringService::class.java
                )

            ContextCompat.startForegroundService(
                this,
                intent
            )
        }

            FCMUtils.saveFcmToken()
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.WHITE
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightNavigationBars = true

        setContent {

            MyApplicationTheme {
                AppNavigation()
            }
        }
    }


}
