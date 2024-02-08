package com.arsalan.location.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arsalan.location.ui.main.viewModel.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val mainUiState by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current
    mainViewModel.fakeData(context)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F1))
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = mainUiState,
            key = { location ->
                location.dateAndTime
            }
        ) { location ->
            Log.d("ArsalanTest1", location.dateAndTime)
            LocationItem(
                lat = location.lat,
                lng = location.lng,
                dateAndTime = location.dateAndTime
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenPreview() {
    MainScreen()
}