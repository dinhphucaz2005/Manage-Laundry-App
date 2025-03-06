package com.example.manage.laundry.di

import android.app.Application
import android.content.Context
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.data.repository.CustomerRepository
import com.example.manage.laundry.data.repository.CustomerRepositoryImpl
import com.example.manage.laundry.data.repository.ShopOwnerRepository
import com.example.manage.laundry.data.repository.ShopOwnerRepositoryImpl
import com.example.manage.laundry.data.repository.StaffRepository
import com.example.manage.laundry.data.repository.StaffRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideCustomerRepository(
        apiService: ApiService
    ): CustomerRepository = CustomerRepositoryImpl(apiService)


    @Provides
    @Singleton
    fun provideOwnerRepository(
        apiService: ApiService
    ): ShopOwnerRepository = ShopOwnerRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideStaffRepository(
        apiService: ApiService
    ): StaffRepository = StaffRepositoryImpl(apiService)
}