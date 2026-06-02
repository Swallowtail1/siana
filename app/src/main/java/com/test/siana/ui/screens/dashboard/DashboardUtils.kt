package com.test.siana.ui.screens.dashboard


fun getFloodStatus(
    waterLevel: Double
): String {

    return when {

        waterLevel >= 200 -> "Bahaya"

        waterLevel >= 150 -> "Waspada"

        else -> "Aman"
    }
}

fun getEarthquakeStatus(
    vibration: Int
): String {

    return when {

        vibration >= 50 -> "Bahaya"

        vibration >= 25 -> "Waspada"

        else -> "Aman"
    }
}

fun getFireStatus(
    temperature: Int
): String {

    return when {

        temperature >= 100 -> "Bahaya"

        temperature >= 60 -> "Waspada"

        else -> "Aman"
    }
}