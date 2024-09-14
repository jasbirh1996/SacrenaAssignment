package com.example.sacrenaassignment.domain.repository

import com.example.sacrenaassignment.utils.NetworkClass
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.ConnectionData
import io.getstream.result.Result

interface AppRepository {

   suspend fun getConnectedResult():NetworkClass<Result<ConnectionData>>

   suspend fun fetchChannelList():NetworkClass<List<Channel>>
}