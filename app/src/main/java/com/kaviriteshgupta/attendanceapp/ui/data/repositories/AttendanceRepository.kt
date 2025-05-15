package com.kaviriteshgupta.attendanceapp.ui.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kaviriteshgupta.attendanceapp.ui.data.NetworkMonitor
import com.kaviriteshgupta.attendanceapp.ui.data.converters.convertLocalAttendanceToRemoteAttendance
import com.kaviriteshgupta.attendanceapp.ui.data.converters.convertRemoteAttendanceToLocalAttendance
import com.kaviriteshgupta.attendanceapp.ui.data.db.AttendanceDao
import com.kaviriteshgupta.attendanceapp.ui.data.models.LocalAttendance
import com.kaviriteshgupta.attendanceapp.ui.data.models.RemoteAttendance
import com.kaviriteshgupta.attendanceapp.ui.di.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AttendanceRepository @Inject constructor(
    private val dao: AttendanceDao,
    private val firestore: FirebaseFirestore,
    private val networkMonitor: NetworkMonitor,
    private val dataStoreManager: DataStoreManager
) {

    fun getLast7DaysAttendance(): Flow<List<LocalAttendance>> = flow {
        val uid = dataStoreManager.getUid()
        val localData = dao.getLast7Days()
            .firstOrNull()

        if (localData.isNullOrEmpty()) {
            if (!uid.isNullOrEmpty()) {
                val daysRef = firestore
                    .collection("attendance_records")
                    .document(uid)
                    .collection("days")

                val snapshot = daysRef
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(7)
                    .get()
                    .await()

                val attendanceList =
                    snapshot.documents.mapNotNull { it.toObject(LocalAttendance::class.java) }

                CoroutineScope(Dispatchers.IO).launch {
                    dao.insertAll(attendanceList)
                }

                emit(attendanceList)
            }
        } else {
            emit(localData)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun markAttendance(record: RemoteAttendance) {
        val uid = dataStoreManager.getUid()
        dao.upsert(convertRemoteAttendanceToLocalAttendance(record))

        if (networkMonitor.isOnline()) {
            try {
                val collectionRef = firestore.collection("attendance_records")
                    .document(uid!!)
                    .collection("days")

                collectionRef.add(record).await()
                dao.markSynced(record.date)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    suspend fun syncOfflineData() {
        if (!networkMonitor.isOnline()) return

        val unsynced = dao.getUnsynced()
        for (record in unsynced) {
            markAttendance(convertLocalAttendanceToRemoteAttendance(record))
        }
    }
}


