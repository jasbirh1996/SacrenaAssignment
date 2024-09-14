package com.example.sacrenaassignment.presentation.viewmodal

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sacrenaassignment.domain.use_cases.ConnectUserAndGetConnectionData
import com.example.sacrenaassignment.presentation.state.ChannelScreenState
import com.example.sacrenaassignment.utils.NetworkClass
import com.example.sacrenaassignment.utils.NetworkDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.models.Attachment
import io.getstream.chat.android.models.Message
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class AppViewModal @Inject constructor(private val connectUserUseCase: ConnectUserAndGetConnectionData, private val networkStatus: NetworkDetector) :
    ViewModel() {

    private var _channelState = mutableStateOf(ChannelScreenState())
    val channelState = _channelState
    var capturedImageUri: Uri? = null
    var channelId: String? = null
    var file: File? = null

    init {
        viewModelScope.launch {

            // connecting user to stream sdk
            // fetching connection data that will help in fetching channels list

            if (networkStatus.isNetworkAvailable()){
                connectUserUseCase.getConnectionResult().let { result ->
                    when (result) {
                        is NetworkClass.Success -> {
                            // call functions to fetch list of channel
                            if (result.data != null) {
                                channelState.value =
                                    ChannelScreenState(connectUserUseCase.fetchChannelList(result.data))

                            }


                        }

                        is NetworkClass.Error -> {
                            // error

                        }

                        is NetworkClass.Loading -> {
                            // can handle loader here
                        }
                    }


                }
            }else{
                _channelState.value = ChannelScreenState(isLoading = true)
            }


        }
    }


    @SuppressLint("SuspiciousIndentation")
    fun uploadImage(context: Context, onClick: () -> Unit) {
        // Get the file from URI
        viewModelScope.launch {
            if (file != null) {
                connectUserUseCase.uploadImage(file!!, channelId ?: "", onClick)

            }
        }
    }
}