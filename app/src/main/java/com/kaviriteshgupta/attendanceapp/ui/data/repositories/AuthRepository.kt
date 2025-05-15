package com.kaviriteshgupta.attendanceapp.ui.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.kaviriteshgupta.attendanceapp.ui.data.db.AttendanceDao
import com.kaviriteshgupta.attendanceapp.ui.di.DataStoreManager
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStoreManager: DataStoreManager,
    private val dao: AttendanceDao
) {

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid
            if (uid != null) {
                dataStoreManager.saveUid(uid)
                Result.success(Unit)
            } else {
                Result.failure(Exception("User ID not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signOutAndResetData(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            dataStoreManager.saveUid("")
            dao.clearTable()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}