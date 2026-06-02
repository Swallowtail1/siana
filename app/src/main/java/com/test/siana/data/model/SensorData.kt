package com.test.siana.data.model

data class SensorData(
    val air_quality: String = "",
    val mq135: Double = 0.0,
    val sig_bmkg: String = "",
    val temperature: Int = 0,
    val timestamp: Long = 0,
    val vibration: Int = 0,
    val water_level: Double = 0.0
)