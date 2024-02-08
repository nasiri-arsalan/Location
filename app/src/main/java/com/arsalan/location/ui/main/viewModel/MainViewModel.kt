package com.arsalan.location.ui.main.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import com.arsalan.location.model.LocationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import com.arsalan.location.utils.Extension.getDateAndTime


@SuppressLint("UnspecifiedRegisterReceiverFlag")
class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<List<LocationModel>>(listOf())
    val uiState: StateFlow<List<LocationModel>> = _uiState.asStateFlow()

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data: Location? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("data", Location::class.java)
            } else {
                intent.getParcelableExtra("data")
            }
            data?.let {
                val myMutableList = mutableListOf<LocationModel>()
                myMutableList.add(
                    LocationModel(
                        lat = it.latitude,
                        lng = it.longitude,
                        dateAndTime = Date().getDateAndTime()
                    )
                )
                _uiState.value += myMutableList
            }
        }
    }

    init {
        registerReceiver()
    }

    private fun registerReceiver() {
        val filter = IntentFilter("service.data.broadcast")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            application.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            application.registerReceiver(receiver, filter)
        }
    }

    override fun onCleared() {
        super.onCleared()
        application.unregisterReceiver(receiver)
    }


}