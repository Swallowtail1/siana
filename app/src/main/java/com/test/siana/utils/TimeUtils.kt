package com.test.siana.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatRelativeTime(
    timestamp: Long
): String {

    val now =
        System.currentTimeMillis()

    val diff =
        now - timestamp

    val minutes =
        diff / (1000 * 60)

    val hours =
        diff / (1000 * 60 * 60)

    val days =
        diff / (1000 * 60 * 60 * 24)

    val timeFormat =
        SimpleDateFormat(
            "HH:mm",
            Locale("id", "ID")
        )

    val time =
        timeFormat.format(
            Date(timestamp)
        )

    return when {

        minutes < 1 ->
            "Baru saja"

        minutes < 60 ->
            "$minutes menit lalu"

        hours < 24 ->
            "$hours jam lalu"

        days == 1L ->
            "Kemarin • $time"

        else ->
        {

            val dateFormat =
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale("id", "ID")
                )

            "${dateFormat.format(Date(timestamp))} • $time"
        }
    }
}