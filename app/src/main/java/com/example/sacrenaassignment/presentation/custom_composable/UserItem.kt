package com.example.sacrenaassignment.presentation.custom_composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import io.getstream.chat.android.models.Channel

@OptIn(InternalStreamChatApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun UserListItem(user: Channel, isOnline: Boolean, onClick: () -> Unit) {



    var name = user.membership?.user?.name ?: "b"

    Column (
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Space between rows
            // Add click listener here
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(48.dp) // Circular image size
            ) {
                if (user.image.isNullOrEmpty()) {
                    // Show the first letter of the user's name
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    ) {
                        Text(
                            text = user.name.first().uppercase(),
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                } else {
                    // Show the user's image
                    Image(
                        painter = rememberAsyncImagePainter(user.image),
                        contentDescription = user.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black), // Fallback background color
                        contentScale = ContentScale.Crop // Crop the image if it's not square
                    )
                }

                // Add the online/offline indicator (green dot)
                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart) // Align the dot to the bottom end
                            .offset(x = (0).dp, y = (-4).dp) // Offset the dot from the edge
                            .size(12.dp) // Size of the dot
                            .clip(CircleShape)
                            .background(Color.Green) // Green color for online
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between image and name

            // Show the user's name
            Column(modifier = Modifier.weight(1f)) {
                // User's name
                Text(
                    text = user.name,
                    fontSize = 18.sp,
                    color = Color.White,
                    maxLines = 1, // Limit name to one line
                    overflow = TextOverflow.Ellipsis // Ellipsis if name is too long
                )

                Spacer(modifier = Modifier.height(4.dp)) // Small space between name and message

                // Last message
                Text(
                    text = name,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1, // Limit message to one line
                    overflow = TextOverflow.Ellipsis // Ellipsis if message is too long
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.fillMaxWidth().height(.5.dp).background(Color.Gray).padding(start = 20.dp, end = 20.dp))
        Spacer(modifier = Modifier.height(5.dp))
    }
}