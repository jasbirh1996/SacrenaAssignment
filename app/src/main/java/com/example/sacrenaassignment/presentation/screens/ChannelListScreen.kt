package com.example.sacrenaassignment.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sacrenaassignment.presentation.custom_composable.UserListItem
import com.example.sacrenaassignment.presentation.ui.theme.AppGrey
import com.example.sacrenaassignment.presentation.viewmodal.AppViewModal

@Composable
fun ChannelListScreen(navController: NavController, vm: AppViewModal) {
    Box(
        modifier = Modifier
            .background(AppGrey)
            .fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 40.dp)
            ) {
                Text(
                    text = "Connections",
                    style = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                items(vm.channelState.value.userDetailList) { user ->
                    UserListItem(user = user, onClick = {
                        // Navigate to chat screen when user clicks on the item
                        navController.navigate("chatScreen/${user.cid}")
                    }, isOnline = true)
                }

            }
        }
    }
}