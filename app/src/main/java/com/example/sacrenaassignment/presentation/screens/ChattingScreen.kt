package com.example.sacrenaassignment.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sacrenaassignment.presentation.custom_composable.DateLabel
import com.example.sacrenaassignment.presentation.custom_composable.ImageCapture
import com.example.sacrenaassignment.presentation.custom_composable.MessageItem
import com.example.sacrenaassignment.presentation.ui.theme.AppGrey
import com.example.sacrenaassignment.presentation.ui.theme.LightGreen
import com.example.sacrenaassignment.presentation.viewmodal.AppViewModal
import com.example.sacrenaassignment.utils.AppUtils.getFormattedDate
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.events.ChatEvent
import io.getstream.chat.android.client.events.NewMessageEvent
import io.getstream.chat.android.client.utils.observable.Disposable
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    channelId: String,
    client: ChatClient,
    onSendMessage: (String) -> Unit, // Callback to send a message
    viewModal: AppViewModal
) {
    val messageList = remember { mutableStateOf<List<Message>>(emptyList()) }
    val user = remember { mutableStateOf<User?>(null) }
    val messageText = remember { mutableStateOf("") } // For text input
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()



    LaunchedEffect(messageList.value.size) {
        // Fetch chat messages and update the list
        val result = client.channel(channelId).watch().execute()
        if (result.isSuccess) {
            result.map {
                messageList.value = it.messages

                it.members.forEach { member ->
                    if (member.user.id != client.getCurrentUser()!!.id) {
                        user.value = member.user
                    }
                }
                listState.animateScrollToItem(messageList.value.size - 1)

            }
        }


        // Subscribe to new message events
        val disposable: Disposable = client.subscribe { event: ChatEvent ->
            when (event) {
                // Check for specific event types
                is NewMessageEvent -> {
                    messageList.value = messageList.value + event.message
                }

                else -> {}
            }

        }


    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppGrey)
            .padding(start = 8.dp, end = 20.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // Space between start, middle, and end
        ) {
            // Back arrow on the left
            IconButton(onClick = {
                navController.popBackStack()
                // Handle back arrow click here
            }) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", tint = Color.White)
            }

            // Image or first letter in the middle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(48.dp) // Circular image size
            ) {
                if (user.value != null) {
                    if (user.value?.image.isNullOrEmpty()) {
                        // Show the first letter of the user's name
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.Cyan)
                        ) {
                            Text(
                                text = user.value!!.name.first().uppercase(),
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        }
                    } else {
                        // Show the user's image
                        Image(
                            painter = rememberAsyncImagePainter(user.value!!.image),
                            contentDescription = user.value!!.name,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.Black), // Fallback background color
                            contentScale = ContentScale.Crop // Crop the image if it's not square
                        )
                    }
                }
            }


            // 3-dot icon on the right
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = {

                }) {
                    Icon(
                        Icons.Default.MoreHoriz,
                        contentDescription = "More options",
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(.5.dp)
                .background(Color.Gray)
        )



        LazyColumn(
            state = listState, // **Highlight**: Link LazyColumn to the scroll state

            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            reverseLayout = false // To show the latest messages at the bottom
        ) {
            var lastDate: String? = null
            items(messageList.value) { message ->
                val currentDate = message.createdAt?.let { getFormattedDate(it) }
                if (currentDate != lastDate) {
                    lastDate = currentDate
                    DateLabel(date = currentDate ?: "Unknown")
                }
                MessageItem(
                    message = message,
                    isSender = message.user.id == client.getCurrentUser()?.id
                )


            }
        }
        Spacer(modifier = Modifier.height(10.dp))


        // Input field and action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Camera button

            Box(modifier = Modifier.padding(bottom = 10.dp)){
                ImageCapture(onClick = { uri, file ->

                    viewModal.capturedImageUri = uri
                    viewModal.channelId = channelId
                    viewModal.file = file



                    navController.navigate("previewScreen")

                })
            }


            Spacer(modifier = Modifier.width(8.dp))

            // Text input for message
            Box(modifier = Modifier
                .weight(1f)
                .padding(bottom = 10.dp, end = 10.dp)
                .height(50.dp),){
                TextField(

                    value = messageText.value,
                    onValueChange = { messageText.value = it },
                    placeholder = { Text("Type a message...", fontSize = 12.sp) },
                    modifier = Modifier,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )

                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Send button

            Box(modifier = Modifier.padding(bottom = 10.dp)){
                IconButton(
                    onClick = {
                        if (messageText.value.isNotBlank()) {
                            onSendMessage(messageText.value)
                            messageText.value = ""
                        }
                    },
                    modifier = Modifier
                        .size(30.dp) // Adjust the size to make it circular
                        .clip(CircleShape)
                        .background(LightGreen)
                        .padding(6.dp) // You can change the background color
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward, // Up arrow icon
                        contentDescription = "Up Arrow",
                        tint = Color.Black // Change the color of the icon if needed
                    )
                }
            }

        }
    }
}