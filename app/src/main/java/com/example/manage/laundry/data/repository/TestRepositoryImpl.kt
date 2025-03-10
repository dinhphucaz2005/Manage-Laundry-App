package com.example.manage.laundry.data.repository

import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.di.repository.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TestRepository {
    override suspend fun test(): String {
        return apiService.test().toString()
    }
}