package com.test.siana.ui.screens.dashboard

import com.test.siana.data.model.DisasterLevel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object DashboardState {

    var currentLevel by mutableStateOf(
        DisasterLevel.AMAN
    )

}