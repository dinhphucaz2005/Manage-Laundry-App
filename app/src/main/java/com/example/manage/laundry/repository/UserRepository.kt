package com.example.manage.laundry.repository

import com.example.manage.laundry.model.User
import com.example.manage.laundry.model.request.LoginRequest
import com.example.manage.laundry.model.response.LoginResponse
import com.example.manage.laundry.network.ApiResponse
import com.example.manage.laundry.network.postApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {

    suspend fun login(
        role: User.Role,
        email: String,
        password: String
    ): ApiResponse<LoginResponse> {
        val endpoint = when (role) {
            User.Role.OWNER -> "/owners/login"
            User.Role.STAFF -> "/staffs/login"
            User.Role.CUSTOMER -> "/customers/login"
        }

        val request = LoginRequest(email, password)
        return postApi(endpoint, request)
    }
}
