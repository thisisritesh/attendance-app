package com.kaviriteshgupta.attendanceapp.ui.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaviriteshgupta.attendanceapp.ui.data.models.LocalAttendance

@Database(entities = [LocalAttendance::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDao
}