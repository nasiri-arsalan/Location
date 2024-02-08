package com.arsalan.location.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.arsalan.location.R
import com.arsalan.location.utils.FindLocation

private const val MY_LOCATION_CHANNEL = "My_Location_Chanel"
private const val NOTIFICATION_ID = 1

class LocationService : LifecycleService() {

    private var broadcastIntent = Intent("service.data.broadcast")
    private lateinit var notification: NotificationCompat.Builder
    private lateinit var findLocation: FindLocation

    override fun onCreate() {
        super.onCreate()

        createNotification()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground()
        }

        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        findLocation = FindLocation(this)
        findLocation.requestLocationUpdates() {
            sendLocation(it)
            updateNotification(it)
        }
    }

    private fun sendLocation(location: Location) {
        broadcastIntent.putExtra("data", location)
        sendBroadcast(broadcastIntent)
    }

    private fun createNotification() {
        notification =
            NotificationCompat.Builder(this, MY_LOCATION_CHANNEL).setSmallIcon(R.mipmap.ic_launcher)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onDestroy() {
        findLocation.stopRequestLocationUpdates()
        super.onDestroy()
    }


    private fun updateNotification(location: Location) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notification.setContentTitle("Location")
            notification.setContentText("${location.latitude}, ${location.longitude}")
            notificationManager.notify(NOTIFICATION_ID, notification.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForeground() {

        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification.build(), FOREGROUND_SERVICE_TYPE_LOCATION)
        } else {
            startForeground(NOTIFICATION_ID, notification.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            MY_LOCATION_CHANNEL,
            "Channel location title",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
    }

}