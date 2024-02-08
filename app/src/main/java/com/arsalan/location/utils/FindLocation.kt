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

class FindLocation(private val context: Context) {
    private var locationCallback: LocationCallback? = null
    private val client = createFusedLocationProviderClient()
    private fun createFusedLocationProviderClient(): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    private fun createLocationRequest(
        priority: Int = Priority.PRIORITY_HIGH_ACCURACY,
        interval: Long = 5000
    ): LocationRequest {
        return LocationRequest.Builder(priority, interval).build()
    }

    fun requestLocationUpdates(onLocationCallback: (Location) -> Unit) {

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


        val locationRequest = createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                onLocationCallback(locationResult.locations.last())
            }
        }
        locationCallback?.let {
            client.requestLocationUpdates(
                locationRequest, it, Looper.getMainLooper()
            )
        }
    }

    fun stopRequestLocationUpdates() {
        locationCallback?.let {
            client.removeLocationUpdates(it)
        }
    }
}