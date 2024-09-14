package com.example.sacrenaassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object AppUtils {
    fun getFormattedDate(date: Date): String {
        val today = Calendar.getInstance()
        val messageCalendar = Calendar.getInstance().apply { time = date }

        return when {
            messageCalendar.isSameDay(today) -> "Today"
            messageCalendar.isYesterday(today) -> "Yesterday"
            else -> SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date)
        }
    }

}