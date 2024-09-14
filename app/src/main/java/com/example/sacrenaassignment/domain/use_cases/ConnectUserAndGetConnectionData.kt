package com.example.sacrenaassignment.domain.use_cases

import com.example.sacrenaassignment.domain.repository.AppRepository
import com.example.sacrenaassignment.utils.NetworkClass
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.ConnectionData
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.chat.android.models.querysort.QuerySortByField.Companion.descByName
import io.getstream.result.Result
import javax.inject.Inject

class ConnectUserAndGetConnectionData @Inject constructor(private val appRepository: AppRepository) {
    suspend fun getConnectionResult():NetworkClass<Result<ConnectionData>>{
      return  appRepository.getConnectedResult()
    }
    suspend fun fetchChannelList(connectionData: Result<ConnectionData>):List<Channel>{
        if(connectionData.isSuccess){
            appRepository.fetchChannelList()
        }
        return  emptyList()

    }
}