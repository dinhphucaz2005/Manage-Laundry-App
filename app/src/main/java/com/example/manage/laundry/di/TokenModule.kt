package com.example.manage.laundry.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Named("authToken")
    fun provideAuthToken(): String {
        return "your_token_here"
    }
}
