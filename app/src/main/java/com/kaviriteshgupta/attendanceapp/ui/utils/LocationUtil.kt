package com.kaviriteshgupta.attendanceapp.ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

fun getCurrentLocation(context: Context, onLocationFetched: (Location?) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    locationClient.lastLocation
        .addOnSuccessListener { location ->
            onLocationFetched(location)
        }
        .addOnFailureListener {
            onLocationFetched(null)
        }
}


fun isWithinRadius(
    userLat: Double,
    userLng: Double,
    officeLat: Double,
    officeLng: Double,
    maxDistanceInMeters: Float = 300f
): Boolean {
    val results = FloatArray(1)
    Location.distanceBetween(userLat, userLng, officeLat, officeLng, results)
    return results[0] <= maxDistanceInMeters
}