package com.kaviriteshgupta.attendanceapp.ui.utils

import com.kaviriteshgupta.attendanceapp.ui.config.PunchingConfig
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentTime(): String =
    SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

fun getTodayDateyyyyMMdd(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

fun isPunchInTimeValid(): Boolean {
    val now = Calendar.getInstance()

    val start = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, PunchingConfig.MIN_PUNCH_IN_TIME)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val end = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, PunchingConfig.MAX_PUNCH_IN_TIME)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    return now.after(start) && now.before(end)
}

fun isPunchOutTimeValid(): Boolean {
    val now = Calendar.getInstance()

    val start = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, PunchingConfig.MIN_PUNCH_OUT_TIME)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val end = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, PunchingConfig.MAX_PUNCH_OUT_TIME)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    return now.after(start) && now.before(end)
}