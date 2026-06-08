package com.test.siana.data.model

data class ThresholdConfig(

    val mq135_max: Int = 1,

    val temp_max: Int = 400,

    val vibration_max: Int = 100,

    val water_max: Double = 200.0
)