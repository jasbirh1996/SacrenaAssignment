package com.example.sacrenaassignment.presentation.custom_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sacrenaassignment.presentation.ui.theme.LightBlack

@Composable
fun DateLabel(date: String) {
    // Box with rounded corners and light black background
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),      // Padding inside the box
        contentAlignment = Alignment.Center
    ) {
        // The date text inside the box
        Text(
            text = date,
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(LightBlack)
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                .wrapContentWidth()
        )
    }
}