package com.example.manage.laundry.data.network

import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService(private val client: HttpClient, private val baseUrl: String) {
    // Shop Owner Endpoints (8)
    suspend fun registerOwner(request: ShopRegisterRequest): ApiResponse<RegisterOwnerResponse> =
        client.post("$baseUrl/owners/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun loginOwner(request: OwnerLoginRequest): ApiResponse<LoginResponse> =
        client.post("$baseUrl/owners/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun addStaff(shopId: Int, request: StaffRegisterRequest): ApiResponse<RegisterStaffResponse> =
        client.post("$baseUrl/owners/shops/$shopId/staffs") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun addService(shopId: Int, request: CreateServiceRequest): ApiResponse<ShopServiceResponse> =
        client.post("$baseUrl/owners/shops/$shopId/services") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun updateService(serviceId: Int, request: UpdateServiceRequest): ApiResponse<ShopServiceResponse> =
        client.put("$baseUrl/owners/services/$serviceId") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun deleteService(serviceId: Int): ApiResponse<Unit> =
        client.delete("$baseUrl/owners/services/$serviceId") {
            contentType(ContentType.Application.Json)
        }.body()

    suspend fun getShopOrders(shopId: Int): ApiResponse<List<ShopOrderResponse>> =
        client.get("$baseUrl/owners/shops/$shopId/orders").body()

    suspend fun updateOrder(orderId: Int, request: UpdateOrderRequest): ApiResponse<OrderResponse> =
        client.put("$baseUrl/owners/orders/$orderId") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    // Staff Endpoints (3)
    suspend fun loginStaff(request: StaffLoginRequest): ApiResponse<StaffLoginResponse> =
        client.post("$baseUrl/staff/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun getStaffOrders(): ApiResponse<List<OrderResponse>> =
        client.get("$baseUrl/staff/orders").body()

    suspend fun updateOrderStatus(orderId: Int, request: UpdateOrderStatusRequest): ApiResponse<OrderResponse> =
        client.put("$baseUrl/staff/orders/$orderId/status") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    // Customer Endpoints (5)
    suspend fun registerCustomer(request: CustomerRegisterRequest): ApiResponse<RegisterCustomerResponse> =
        client.post("$baseUrl/customers/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun loginCustomer(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse> =
        client.post("$baseUrl/customers/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun searchShops(): ApiResponse<List<ShopSearchResponse>> =
        client.get("$baseUrl/customers/shops").body()

    suspend fun getOrderHistory(): ApiResponse<List<OrderHistoryResponse>> =
        client.get("$baseUrl/customers/orders").body()

    suspend fun trackOrder(orderId: Int): ApiResponse<TrackOrderResponse> =
        client.get("$baseUrl/customers/orders/$orderId/track").body()
}