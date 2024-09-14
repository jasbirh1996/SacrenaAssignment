package com.example.sacrenaassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sacrenaassignment.presentation.ui.theme.SacrenaAssignmentTheme
import com.example.sacrenaassignment.presentation.viewmodal.AppViewModal
import io.getstream.chat.android.models.Message

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SacrenaAssignmentTheme {
                val vm = hiltViewModel<AppViewModal>()
                val navController = rememberNavController()

                // Define navigation routes
                NavHost(navController = navController, startDestination = "channelListScreen") {
                    composable("channelListScreen") {
                        ChannelListScreen(navController, vm)
                    }
                    composable("chatScreen/{channelId}") { backStackEntry ->
                        val channelId = backStackEntry.arguments?.getString("channelId")
                        // Pass the channelId to ChatScreen and load chat data for the channel

                        if (channelId != null) {
                            ChatScreen(
                                navController,
                                channelId = channelId,
                                client = client,
                                onSendMessage = { message ->
                                    val channel = client.channel(channelId)
                                    val msg = Message(text = message)
                                    channel.sendMessage(msg).enqueue { result ->
                                        if (result.isSuccess) {


                                            // Clear the input after successful send
                                        }
                                    }
                                }, viewModal = vm
                            )
                        }

                    }

                    composable("previewScreen") { backStackEntry ->
                        vm.capturedImageUri
                        PreviewImage(
                            navController,
                            viewModal = vm
                        )

                    }
                }
            }
        }
    }
}

