package com.arsalan.location.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arsalan.location.ui.main.viewModel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
    modifier: Modifier
) {
    val mainUiState by mainViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        reverseLayout = true,
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F1))
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        items(
            items = mainUiState,
            key = { location ->
                location.dateAndTime
            }
        ) { location ->
            coroutineScope.launch {
                listState.animateScrollToItem(mainUiState.lastIndex)
            }
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
    MainScreen(modifier = Modifier.padding(16.dp))
}