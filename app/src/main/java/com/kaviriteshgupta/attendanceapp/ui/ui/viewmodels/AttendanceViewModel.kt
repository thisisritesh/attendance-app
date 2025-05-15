package com.kaviriteshgupta.attendanceapp.ui.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.Timestamp
import com.kaviriteshgupta.attendanceapp.ui.data.models.LocalAttendance
import com.kaviriteshgupta.attendanceapp.ui.data.models.RemoteAttendance
import com.kaviriteshgupta.attendanceapp.ui.data.repositories.AttendanceRepository
import com.kaviriteshgupta.attendanceapp.ui.data.repositories.AuthRepository
import com.kaviriteshgupta.attendanceapp.ui.di.DataStoreManager
import com.kaviriteshgupta.attendanceapp.ui.domain.work.AttendanceSyncWorker
import com.kaviriteshgupta.attendanceapp.ui.utils.getCurrentTime
import com.kaviriteshgupta.attendanceapp.ui.utils.getTodayDateyyyyMMdd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val attendanceRepo: AttendanceRepository,
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {

    val punchInTime = dataStore.punchInTimeFlow.asLiveData()

    private val _weeklyAttendance = MutableStateFlow<List<LocalAttendance>>(emptyList())
    val weeklyAttendance: StateFlow<List<LocalAttendance>> = _weeklyAttendance

    fun getWeeklyAttendance() {
        viewModelScope.launch {
            attendanceRepo.getLast7DaysAttendance().collect({
                _weeklyAttendance.value = it
            })
        }
    }


    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                callback(true, "")
            } else {
                callback(false, "Authentication Error!")
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            authRepository.signOutAndResetData()
        }
    }

    fun savePunchInTime() {
        viewModelScope.launch {
            dataStore.savePunchInTime(getCurrentTime())
        }
    }

    fun markToday() = viewModelScope.launch {

        val punchInTime = dataStore.getPunchInTime()

        val today = getTodayDateyyyyMMdd()
        val record = RemoteAttendance(
            today,
            punchInTime,
            getCurrentTime(),
            "Present",
            Timestamp(Date())
        )
        attendanceRepo.markAttendance(record)
    }

    fun enqueueSyncWork(context: Context) {
        val syncRequest = OneTimeWorkRequestBuilder<AttendanceSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(syncRequest)
    }


}
