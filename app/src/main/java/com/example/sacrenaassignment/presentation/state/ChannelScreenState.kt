package com.example.sacrenaassignment.presentation.state

import io.getstream.chat.android.models.Channel

data class ChannelScreenState(
    val userDetailList : List<Channel> =  emptyList(),
    val isLoading : Boolean = false,
    val error : String = "",

    )