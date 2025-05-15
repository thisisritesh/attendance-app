package com.kaviriteshgupta.attendanceapp.ui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
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
import com.kaviriteshgupta.attendanceapp.ui.ui.getStatusBgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.getStatusTextColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.bgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.cardBgColor
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.primaryColor

@Composable
fun WeeklyAttendanceTable(attendanceList: List<LocalAttendance>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardBgColor)
            ) {
                TableCell(Modifier.weight(1f), "Date", true, cardBgColor, primaryColor)
                TableCell(Modifier.weight(1f), "Status", true, cardBgColor, primaryColor)
                TableCell(Modifier.weight(1f), "Punch In", true, cardBgColor, primaryColor)
                TableCell(Modifier.weight(1f), "Punch Out", true, cardBgColor, primaryColor)
            }
            HorizontalDivider()
        }

        items(attendanceList) { attendance ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TableCell(
                    text = attendance.date,
                    bgColor = bgColor,
                    color = primaryColor,
                    modifier = Modifier.weight(1f)
                )
                TableCell(
                    text = attendance.status,
                    bgColor = getStatusBgColor(attendance.status),
                    color = getStatusTextColor(attendance.status),
                    modifier = Modifier.weight(1f)
                )
                TableCell(
                    text = attendance.punch_in,
                    bgColor = bgColor,
                    color = primaryColor,
                    modifier = Modifier.weight(1f)
                )
                TableCell(
                    text = attendance.punch_out,
                    bgColor = bgColor,
                    color = primaryColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun TableCell(
    modifier: Modifier,
    text: String,
    bold: Boolean = false,
    bgColor: Color,
    color: Color
) {
    Text(
        text = text,
        modifier = modifier
            .background(bgColor)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        fontFamily = FontFamily(
            Font(
                if (bold) R.font.montserrat_semibold else R.font.montserrat_medium
            )
        ),
        fontSize = TextUnit(14f, TextUnitType.Sp),
        color = color
    )
}