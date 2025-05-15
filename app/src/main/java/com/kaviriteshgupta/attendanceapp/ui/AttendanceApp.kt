package com.kaviriteshgupta.attendanceapp.ui

import android.app.Application
import androidx.work.Configuration
import com.kaviriteshgupta.attendanceapp.ui.di.DataStoreManager
import com.kaviriteshgupta.attendanceapp.ui.domain.work.AttendanceWorkerFactory
import com.kaviriteshgupta.attendanceapp.ui.utils.getTodayDateyyyyMMdd
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AttendanceApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: AttendanceWorkerFactory

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        val lastResetDate = dataStoreManager.getLastResetDate()
        val today = getTodayDateyyyyMMdd()

        if (lastResetDate != today) {
            GlobalScope.launch {
                dataStoreManager.savePunchInTime("")
                dataStoreManager.saveLastResetDate(today)
            }
        }
    }

}