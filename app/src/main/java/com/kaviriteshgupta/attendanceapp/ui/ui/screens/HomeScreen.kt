package com.kaviriteshgupta.attendanceapp.ui.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.kaviriteshgupta.attendanceapp.R
import com.kaviriteshgupta.attendanceapp.ui.ui.AppButton
import com.kaviriteshgupta.attendanceapp.ui.ui.AppSpacer
import com.kaviriteshgupta.attendanceapp.ui.ui.getPreviewTableData
import com.kaviriteshgupta.attendanceapp.ui.ui.viewmodels.AttendanceViewModel
import com.kaviriteshgupta.attendanceapp.ui.config.OfficeLocationConfig
import com.kaviriteshgupta.attendanceapp.ui.utils.getCurrentLocation
import com.kaviriteshgupta.attendanceapp.ui.utils.isPunchInTimeValid
import com.kaviriteshgupta.attendanceapp.ui.utils.isPunchOutTimeValid
import com.kaviriteshgupta.attendanceapp.ui.utils.isWithinRadius

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: AttendanceViewModel, context: Context, logoutCallback: () -> Unit) {
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Weekly Attendance Report",
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = TextUnit(28f, TextUnitType.Sp),
                    lineHeight = TextUnit(32f, TextUnitType.Sp)
                )

                Icon(
                    Icons.AutoMirrored.Default.Logout,
                    contentDescription = null,
                    modifier = Modifier
                        .align(
                            Alignment.CenterEnd
                        )
                        .clickable {
                            viewModel.logOut()
                            logoutCallback()
                        }
                )
            }

            viewModel.getWeeklyAttendance()
            val attendanceState by viewModel.weeklyAttendance.collectAsState()
            WeeklyAttendanceTable(attendanceState)

            AppSpacer(10)

            val showPreview: MutableState<Boolean> = remember { mutableStateOf(false) }

            OutlinedButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    showPreview.value = !showPreview.value
                }
            ) {
                Text(if(showPreview.value) "Hide preview" else "Show preview")
            }

            if(showPreview.value) {
                WeeklyAttendanceTable(attendanceList = getPreviewTableData())
            }

        }


        var permissionGranted by remember { mutableStateOf(false) }

        if (!permissionGranted) {
            RequestLocationPermission(
                onPermissionGranted = { permissionGranted = true },
                onPermissionDenied = {
                    // Show error or alternative UI
                }
            )
        }

        if (permissionGranted) {
            PunchingButton(modifier = Modifier.align(Alignment.BottomCenter), viewModel, context)
        }


    }
}

@Composable
fun PunchingButton(modifier: Modifier, viewModel: AttendanceViewModel, context: Context) {
    val punchInTime by viewModel.punchInTime.observeAsState()
    var btnText = "Punch Out"
    if (punchInTime.isNullOrEmpty()) {
        btnText = "Punch In"
    }

    var showDialog by remember { mutableStateOf(false) }
    var dialogBodyMessage by remember { mutableStateOf("") }



    AppButton(
        btnText, modifier = modifier
            .padding(16.dp)
    ) {
        attemptPunch(context, btnText, viewModel) { shouldShowDialog, message ->
            showDialog = shouldShowDialog
            dialogBodyMessage = message
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Text(
                    text = dialogBodyMessage
                )
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

fun attemptPunch(
    context: Context,
    action: String,
    viewModel: AttendanceViewModel,
    onResult: (Boolean, String) -> Unit
) {
    getCurrentLocation(context) { location ->
        Log.d("schuewcnej", "latitude: " + location?.latitude)
        Log.d("schuewcnej", "longitude: " + location?.longitude)
        if (location == null) {
            return@getCurrentLocation
        }

        val isValidLocation = isWithinRadius(
            location.latitude,
            location.longitude,
            OfficeLocationConfig.OFFICE_LATITUDE,
            OfficeLocationConfig.OFFICE_LONGITUDE
        )

        if (!isValidLocation) {
            onResult(true, "Not within 300 meters of office")
            return@getCurrentLocation
        }

        if (action == "Punch In") {
            if (isPunchInTimeValid()) {
                viewModel.savePunchInTime()
                onResult(true, "Punched In successfully")
                viewModel.getWeeklyAttendance()
                viewModel.enqueueSyncWork(context)
                return@getCurrentLocation
            } else {
                onResult(true, "Invalid time to Punch In")
                return@getCurrentLocation
            }
        } else {
            if (isPunchOutTimeValid()) {
                viewModel.markToday()
                onResult(true, "Punched Out successfully")
                viewModel.getWeeklyAttendance()
                viewModel.enqueueSyncWork(context)
                return@getCurrentLocation
            } else {
                onResult(true, "Invalid time to Punch Out")
                return@getCurrentLocation
            }
        }
    }
}


@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                onPermissionGranted()
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}