package com.test.siana.ui.screens.dashboard

import com.test.siana.utils.ThresholdManager


fun getFloodStatus(
    waterLevel: Double
): String {

    return when {

        waterLevel >= ThresholdManager.config.water_max -> "Bahaya"

        waterLevel >= 50 -> "Waspada"

        else -> "Aman"
    }
}

fun getEarthquakeStatus(
    vibration: Int
): String {

    return when {

        vibration >= ThresholdManager.config.vibration_max -> "Bahaya"

        vibration >= 25 -> "Waspada"

        else -> "Aman"
    }
}

fun getFireStatus(
    temperature: Int
): String {

    return when {

        temperature >= ThresholdManager.config.temp_max -> "Bahaya"

        temperature >= 50 -> "Waspada"

        else -> "Aman"
    }
}