package com.kaviriteshgupta.attendanceapp.ui.domain.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kaviriteshgupta.attendanceapp.ui.data.repositories.AttendanceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AttendanceSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AttendanceRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            repository.syncOfflineData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}