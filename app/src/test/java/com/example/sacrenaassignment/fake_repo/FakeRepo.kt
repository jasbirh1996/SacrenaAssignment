package com.example.sacrenaassignment.fake_repo

import com.example.sacrenaassignment.domain.repository.AppRepository
import com.example.sacrenaassignment.utils.NetworkClass
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.ConnectionData
import io.getstream.result.Result
import java.io.File

class  FakeRepo :AppRepository{
    private var shouldGiveException = false
    fun setShouldGiveException(value: Boolean) {
        shouldGiveException = value
    }
    override suspend fun getConnectedResult(): NetworkClass<Result<ConnectionData>> {
        if(shouldGiveException){
            return  NetworkClass.Error(null, "unexpected error")
        }
      return  NetworkClass.Success(null)
    }

    override suspend fun fetchChannelList(): NetworkClass<List<Channel>> {
        if(shouldGiveException){
            return NetworkClass.Error()
        }
        return NetworkClass.Success(emptyList())
    }

    override suspend fun uploadImage(file: File, channelId: String, onClick: () -> Unit) {

    }

}