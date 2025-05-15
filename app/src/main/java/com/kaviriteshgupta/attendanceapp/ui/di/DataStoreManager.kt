package com.kaviriteshgupta.attendanceapp.ui.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "attendance_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val punchInTimeFlow: Flow<String?> = dataStore.data.map { it[PUNCH_IN_TIME_KEY] }

    suspend fun saveUid(uid: String) {
        dataStore.edit { prefs ->
            prefs[UID_KEY] = uid
        }
    }

    fun getUid(): String? {
        return runBlocking {
            dataStore.data.first()[UID_KEY]
        }
    }

    suspend fun saveLastResetDate(uid: String) {
        dataStore.edit { prefs ->
            prefs[LAST_RESET_DATE_KEY] = uid
        }
    }

    fun getLastResetDate(): String? {
        return runBlocking {
            dataStore.data.first()[LAST_RESET_DATE_KEY]
        }
    }

    suspend fun savePunchInTime(punchInTime: String) {
        dataStore.edit { prefs ->
            prefs[PUNCH_IN_TIME_KEY] = punchInTime
        }
    }

    fun getPunchInTime(): String? {
        return runBlocking {
            dataStore.data.first()[PUNCH_IN_TIME_KEY]
        }
    }

    companion object {
        private val UID_KEY = stringPreferencesKey("uid_key")
        private val PUNCH_IN_TIME_KEY = stringPreferencesKey("punch_in_time_key")
        private val LAST_RESET_DATE_KEY = stringPreferencesKey("last_reset_date_key")
    }


}
