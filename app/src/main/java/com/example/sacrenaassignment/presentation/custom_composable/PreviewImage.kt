package com.example.sacrenaassignment.presentation.custom_composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sacrenaassignment.R
import com.example.sacrenaassignment.presentation.viewmodal.AppViewModal

@Composable
fun PreviewImage(navController: NavController, viewModal: AppViewModal){
    val context = LocalContext.current
    if(viewModal.capturedImageUri?.path?.isNotEmpty()==true )
    {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
            contentAlignment = Alignment.Center){
            Column {
                Image(
                    modifier = Modifier.padding(
                        8.dp
                    ),
                    painter = rememberImagePainter(viewModal.capturedImageUri),
                    contentDescription = null
                )
                Button(onClick = {
                    viewModal.uploadImage(context,{
                        navController.popBackStack()
                    })

                }) {
                    Text(text = "Send", color = Color.White)
                }

            }

        }
    }else
    {
        Image(
            modifier = Modifier.padding(
                8.dp
            ),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )

    }
}