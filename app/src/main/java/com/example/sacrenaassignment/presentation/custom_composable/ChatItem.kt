package com.example.sacrenaassignment.presentation.custom_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.models.Message
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MessageItem(message: Message, isSender: Boolean) {
    val backgroundColor =
        if (isSender) Color(0xFFD2F8D2) else Color.White // Light green for sender, white for receiver
    val shape = if (isSender) {
        // **Highlight**: Custom rounded border for sender's message
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)

    } else {
        // **Highlight**: Different rounded border for receiver's message
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }

    // Format message time as "hh:mm a"
    val messageTime = remember(message) {
        message.createdAt?.let { date ->
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date)
        } ?: "Unknown"
    }

    // chat item
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .wrapContentWidth(if (isSender) Alignment.End else Alignment.Start),
    ) {
        Column(
            horizontalAlignment = if (isSender) Alignment.End else Alignment.Start // Align text and time based on sender/receiver
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundColor, shape) // Apply background color and shape
                    .padding(10.dp)
                    .wrapContentWidth()
            ) {

                Column {
                    if (message.attachments.isNotEmpty()) {
                        ImageCard(imageUrl = message.attachments.first().imageUrl.toString())
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = messageTime,
                            color = Color.Gray, // Gray color for the timestamp
                            fontSize = 12.sp, // Smaller font size for the time
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.End) // Space between the message and time
                        )

                    } else {
                        Text(
                            text = message.text,
                            color = if (isSender) Color.Black else Color.DarkGray, // Different text color for sender and receiver
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = messageTime,
                            color = Color.Gray, // Gray color for the timestamp
                            fontSize = 12.sp, // Smaller font size for the time
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(Alignment.End) // Space between the message and time
                        )
                    }

                }


            }

            // Display the message time below the message

        }
    }


}