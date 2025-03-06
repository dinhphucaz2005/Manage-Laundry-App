package com.example.manage.laundry.data.network

sealed class ApiWrapper<T> {
    data class Success<T>(val data: T) : ApiWrapper<T>()
}