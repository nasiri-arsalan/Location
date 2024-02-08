package com.arsalan.location.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class FindLocation {
    private fun createFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    private fun createLocationRequest(
        priority: Int = Priority.PRIORITY_HIGH_ACCURACY,
        interval: Long = 5000
    ): LocationRequest {
        return LocationRequest.Builder(priority, interval).build()
    }

    fun requestLocationUpdates(context: Context, onLocationCallback: (Location) -> Unit) {

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

        val client = createFusedLocationProviderClient(context = context)

        val locationRequest = createLocationRequest()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                onLocationCallback(locationResult.locations.last())
            }
        }
        client.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }
}