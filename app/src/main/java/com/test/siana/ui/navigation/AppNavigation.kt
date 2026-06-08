package com.test.siana.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.siana.ui.screens.auth.AuthScreen
import com.test.siana.ui.screens.dashboard.DashboardScreen
import com.test.siana.ui.screens.profile.ProfileScreen
import com.test.siana.ui.screens.notification.NotificationScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val startDestination =
        if (FirebaseAuth.getInstance().currentUser != null)

            "dashboard"
        else
            "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    )  {

        composable("login") {
            AuthScreen(navController)

        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("notification") {
            NotificationScreen(navController)
        }

    }

}