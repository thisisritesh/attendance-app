package com.kaviriteshgupta.attendanceapp.ui.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaviriteshgupta.attendanceapp.ui.data.models.LocalAttendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(record: LocalAttendance)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attendanceList: List<LocalAttendance>)

    @Query("SELECT * FROM attendance_table ORDER BY date DESC LIMIT 7")
    fun getLast7Days(): Flow<List<LocalAttendance>>

    @Query("SELECT * FROM attendance_table WHERE synced = 0")
    suspend fun getUnsynced(): List<LocalAttendance>

    @Query("UPDATE attendance_table SET synced = 1 WHERE date = :date")
    suspend fun markSynced(date: String)

    @Query("DELETE FROM attendance_table")
    suspend fun clearTable()
}