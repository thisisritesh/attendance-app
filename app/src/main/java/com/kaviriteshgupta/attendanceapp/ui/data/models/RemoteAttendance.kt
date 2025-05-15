package com.kaviriteshgupta.attendanceapp.ui.data.models

import com.google.firebase.Timestamp
import java.util.Date

data class RemoteAttendance(
    var date: String,
    var punch_in: String?,
    var punch_out: String,
    var status: String,
    var syncedAt: Timestamp
) {
    constructor() : this("","","","",Timestamp(Date()))
}