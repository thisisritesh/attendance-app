package com.kaviriteshgupta.attendanceapp.ui.data.converters

import com.google.firebase.Timestamp
import com.kaviriteshgupta.attendanceapp.ui.data.models.LocalAttendance
import com.kaviriteshgupta.attendanceapp.ui.data.models.RemoteAttendance
import java.util.Date

fun convertLocalAttendanceToRemoteAttendance(localAttendance: LocalAttendance): RemoteAttendance {
    return RemoteAttendance(
        localAttendance.date,
        localAttendance.punch_in,
        localAttendance.punch_out,
        localAttendance.status,
        Timestamp(Date())
    )
}

fun convertRemoteAttendanceToLocalAttendance(remoteAttendance: RemoteAttendance): LocalAttendance {
    return LocalAttendance(
        remoteAttendance.date,
        remoteAttendance.punch_in!!,
        remoteAttendance.punch_out,
        remoteAttendance.status,
        false
    )
}