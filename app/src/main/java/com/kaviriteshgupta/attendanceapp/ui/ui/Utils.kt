package com.kaviriteshgupta.attendanceapp.ui.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kaviriteshgupta.attendanceapp.R
import com.kaviriteshgupta.attendanceapp.ui.data.models.LocalAttendance
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.cardBgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.dangerRedBgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.dangerRedColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.secondaryTextColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.successGreenBgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.successGreenColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.yellowBgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.yellowColor


@Composable
fun AppSpacer(size: Int) {
    Spacer(modifier = Modifier.size(size.dp))
}

@Composable
fun AppButton(btnTxt: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(8.dp), onClick = {
            onClick()
        }, modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = btnTxt.uppercase(),
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontFamily = FontFamily(
                Font(R.font.montserrat_semibold)
            )
        )
    }
}


fun getStatusTextColor(status: String): Color {
    return when (status) {
        "Late" -> yellowColor
        "Absent" -> dangerRedColor
        "Present" -> successGreenColor
        "Not marked" -> secondaryTextColor
        else -> secondaryTextColor
    }
}

fun getStatusBgColor(status: String): Color {
    return when (status) {
        "Late" -> yellowBgColor
        "Absent" -> dangerRedBgColor
        "Present" -> successGreenBgColor
        "Not marked" -> cardBgColor
        else -> successGreenBgColor
    }
}


fun getPreviewTableData() : List<LocalAttendance> {
    return listOf(
        LocalAttendance("2025-05-15", "9:00 AM", "10:00 PM", "Present"),
        LocalAttendance("2025-05-15", "00:00 AM", "00:00 PM", "Absent"),
        LocalAttendance("2025-05-15", "9:00 AM", "10:00 PM", "Present"),
        LocalAttendance("2025-05-15", "9:00 AM", "10:00 PM", "Absent"),
        LocalAttendance("2025-05-15", "00:00 AM", "00:00 PM", "Holiday"),
        LocalAttendance("2025-05-15", "00:00 AM", "00:00 PM", "Absent"),
        LocalAttendance("2025-05-15", "12:00 AM", "10:00 PM", "Late")
    )
}