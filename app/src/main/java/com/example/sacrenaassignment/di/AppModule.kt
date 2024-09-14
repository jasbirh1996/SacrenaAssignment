package com.example.sacrenaassignment.di


import android.content.Context
import com.example.sacrenaassignment.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOfflinePluginFactory(@ApplicationContext context: Context): StreamOfflinePluginFactory {
        return StreamOfflinePluginFactory(appContext = context)
    }

    @Provides
    @Singleton
    fun provideStatePluginFactory(@ApplicationContext context: Context): StreamStatePluginFactory {
        return StreamStatePluginFactory(config = StatePluginConfig(), appContext = context)
    }

    // providing dependency for  chatClient
    @Provides
    @Singleton
    fun provideChatClient(
        @ApplicationContext context: Context,
        offlinePluginFactory: StreamOfflinePluginFactory,
        statePluginFactory: StreamOfflinePluginFactory
    ): ChatClient {
        return ChatClient.Builder(AppConstants.API_KEY, context)
            .withPlugins(offlinePluginFactory, statePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()
    }
}