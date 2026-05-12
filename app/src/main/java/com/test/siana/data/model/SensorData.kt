package com.test.siana.data.model

data class SensorData(
    val suhu: Int,
    val udara: Int,
    val banjir: Boolean,
    val gempa: Boolean,
    val level: DisasterLevel
)