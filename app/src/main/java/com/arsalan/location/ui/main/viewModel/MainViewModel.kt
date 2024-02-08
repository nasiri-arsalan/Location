package com.arsalan.location.ui.main.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.arsalan.location.model.LocationModel
import com.arsalan.location.utils.FindLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<List<LocationModel>>(listOf())
    val uiState: StateFlow<List<LocationModel>> = _uiState.asStateFlow()


    // TODO: Delete after add service
    fun fakeData(context: Context) {
        val findLocation = FindLocation()
        findLocation.requestLocationUpdates(context) {
            val myMutableList = mutableListOf<LocationModel>()
            myMutableList.add(
                LocationModel(
                    lat = it.latitude,
                    lng = it.longitude,
                    dateAndTime = Date().toString()
                )
            )
            _uiState.value += myMutableList
            Log.d(
                "ArslanTest",
                "${_uiState.value.size}  ${myMutableList.size}   ${if (_uiState.value.isNotEmpty()) _uiState.value.last().dateAndTime else ""}"
            )
        }
    }
}