package com.kaviriteshgupta.attendanceapp.ui.domain.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.kaviriteshgupta.attendanceapp.ui.data.repositories.AttendanceRepository
import javax.inject.Inject

class AttendanceWorkerFactory(
    private val repository: AttendanceRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            AttendanceSyncWorker::class.qualifiedName -> {
                AttendanceSyncWorker(appContext, workerParameters, repository)
            }
            else -> null
        }
    }
}
