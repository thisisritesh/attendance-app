package com.kaviriteshgupta.attendanceapp.ui.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkerFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kaviriteshgupta.attendanceapp.ui.data.NetworkMonitor
import com.kaviriteshgupta.attendanceapp.ui.data.db.AppDatabase
import com.kaviriteshgupta.attendanceapp.ui.data.db.AttendanceDao
import com.kaviriteshgupta.attendanceapp.ui.data.repositories.AttendanceRepository
import com.kaviriteshgupta.attendanceapp.ui.domain.work.AttendanceWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AttendanceAppModules {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "attendance_db"
        ).fallbackToDestructiveMigration(true).build()

    @Provides
    fun provideAttendanceDao(db: AppDatabase): AttendanceDao = db.attendanceDao()

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitor(context)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Provides
    @Singleton
    fun provideAttendanceWorkerFactory(
        repository: AttendanceRepository
    ): AttendanceWorkerFactory {
        return AttendanceWorkerFactory(repository)
    }


}