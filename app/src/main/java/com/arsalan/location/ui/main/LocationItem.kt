package com.arsalan.location.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationItem(lat: Double, lng: Double, dateAndTime: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dateAndTime,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Location: $lat, $lng",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LocationItemPreview() {
    LocationItem(lat = 10000.0, lng = 300000.0, dateAndTime = "11 may 24 11:20:30")
}