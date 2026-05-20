package com.test.siana.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.siana.ui.screens.auth.AuthScreen
import com.test.siana.ui.screens.dashboard.DashboardScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            AuthScreen(navController)

        }

        composable("dashboard") {
            DashboardScreen()
        }
    }

}