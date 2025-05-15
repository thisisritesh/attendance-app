package com.kaviriteshgupta.attendanceapp.ui.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_table")
data class LocalAttendance(
    @PrimaryKey val date: String,
    val punch_in: String,
    val punch_out: String,
    val status: String,
    val synced: Boolean = false
) {
    constructor() : this("","","","",false)
}