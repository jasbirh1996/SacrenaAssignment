package com.example.sacrenaassignment.utils

import android.content.Context
import io.getstream.chat.android.models.Channel
import io.getstream.result.Result
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


fun Calendar.isSameDay(other: Calendar): Boolean {
    return this.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            this.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)
}

fun Calendar.isYesterday(today: Calendar): Boolean {
    today.add(Calendar.DAY_OF_YEAR, -1)
    return this.isSameDay(today)
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image

}
