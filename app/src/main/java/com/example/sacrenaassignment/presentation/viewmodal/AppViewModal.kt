package com.example.sacrenaassignment.presentation.viewmodal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sacrenaassignment.domain.use_cases.ConnectUserAndGetConnectionData
import com.example.sacrenaassignment.presentation.state.ChannelScreenState
import com.example.sacrenaassignment.utils.NetworkClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppViewModal @Inject constructor(private val connectUserUseCase : ConnectUserAndGetConnectionData) :ViewModel(){

    private var _channelState = mutableStateOf(ChannelScreenState())
    val channelState = _channelState
    init {
        viewModelScope.launch {

            // connecting user to stream sdk
            // fetching connection data that will help in fetching channels list
            connectUserUseCase.getConnectionResult().let{result ->
                when(result){
                    is NetworkClass.Success ->{
                        // call functions to fetch list of channel
                        if(result.data!=null){
                           channelState.value = ChannelScreenState(connectUserUseCase.fetchChannelList(result.data))

                        }




                    }
                    is NetworkClass.Error->{
                        // error

                    }
                    is NetworkClass.Loading->{
                        // can handle loader here
                    }
                }


            }

        }
    }
}