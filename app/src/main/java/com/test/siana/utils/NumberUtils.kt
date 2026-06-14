package com.test.siana.utils

fun Double.cleanNumber(): String {
    return if (this % 1.0 == 0.0) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}