package com.example.sacrenaassignment.presentation.custom_composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageCard(imageUrl : String){
    Box(modifier = Modifier.size(200.dp).padding(10.dp)){
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "item image",
            modifier = Modifier
                .fillMaxSize()

                .background(Color.Black), // Fallback background color
            contentScale = ContentScale.Crop // Crop the image if it's not square
        )
    }
}