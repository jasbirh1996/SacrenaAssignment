package com.example.sacrenaassignment.data.repository

import android.util.Log
import android.widget.Toast
import com.example.sacrenaassignment.domain.repository.AppRepository
import com.example.sacrenaassignment.utils.AppConstants
import com.example.sacrenaassignment.utils.NetworkClass
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.models.Attachment
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.ConnectionData
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.models.User
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.chat.android.models.querysort.QuerySortByField.Companion.descByName
import io.getstream.result.Result
import java.io.File
import javax.inject.Inject

class AppRepositoryImp @Inject constructor(private val client: ChatClient) : AppRepository {
    override suspend fun getConnectedResult(): NetworkClass<Result<ConnectionData>> {
        val user = User(
            id = AppConstants.USER_ID,
            name = AppConstants.USER_NAME,
            image = AppConstants.USER_IMAGE
        )
        val token = client.devToken(user.id)
        val result = client.connectUser(user = user, token = token).execute()
        return if (result.isSuccess) {
            NetworkClass.Success(data = result)

        } else {
            NetworkClass.Error(
                message = result.errorOrNull()?.message ?: "something went wrong",
                data = null
            )

        }

    }

    override suspend fun fetchChannelList(): NetworkClass<List<Channel>> {
        // adding params to get channel based on filter
        val filter = Filters.and(
            Filters.eq("type", "messaging"), // Fix filter issue
            Filters.`in`("members", listOf(client.getCurrentUser()?.id ?: ""))
        )
        //sorting
        val sort = QuerySortByField<Channel>().descByName("lastMessageAt")
// request
        val queryChannelsRequest = QueryChannelsRequest(
            filter = filter,
            limit = 10, // Adjust limit as needed
            querySort = sort
        )
        // Query channels
        val channelListResult = client.queryChannels(queryChannelsRequest).execute()

        return if (channelListResult.isSuccess) {
            val data = channelListResult.getOrNull()
            NetworkClass.Success(data)


        } else {
            NetworkClass.Error(message = channelListResult.errorOrNull()?.message?:"something went wrong")
            // Handle the error case
        }
    }

    override suspend fun uploadImage(file: File, channelId: String,onClick: ()->Unit) {
        val result =  client.channel(channelId).sendImage(file).execute()
        if (result.isSuccess){
            result.map {
                val attachment = Attachment(
                    type = "image",
                    imageUrl = it.file,
                )
                val message = Message(
                    attachments = mutableListOf(attachment),
                )
                val data = client.channel(channelId).sendMessage(message = message).execute()
                if(data.isSuccess){
                    onClick()
                    Log.e("fileUploaded","success")
                }

            }

        }else{
            onClick()
            Log.e("error","image not uploaded")
        }
    }


}