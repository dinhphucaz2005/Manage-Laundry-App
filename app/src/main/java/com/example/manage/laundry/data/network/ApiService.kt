package com.example.manage.laundry.data.network

import com.example.manage.laundry.data.model.request.CancelOrderRequest
import com.example.manage.laundry.data.model.request.ConfirmOrderRequest
import com.example.manage.laundry.data.model.request.CreateOrderRequest
import com.example.manage.laundry.data.model.request.CreateServiceRequest
import com.example.manage.laundry.data.model.request.CustomerLoginRequest
import com.example.manage.laundry.data.model.request.CustomerRegisterRequest
import com.example.manage.laundry.data.model.request.Order
import com.example.manage.laundry.data.model.request.OwnerLoginRequest
import com.example.manage.laundry.data.model.request.ShopRegisterRequest
import com.example.manage.laundry.data.model.request.StaffLoginRequest
import com.example.manage.laundry.data.model.request.StaffRegisterRequest
import com.example.manage.laundry.data.model.request.UpdateOrderRequest
import com.example.manage.laundry.data.model.request.UpdateOrderStatusRequest
import com.example.manage.laundry.data.model.request.UpdateServiceRequest
import com.example.manage.laundry.data.model.response.ApiResponse
import com.example.manage.laundry.data.model.response.CreateOrderResponse
import com.example.manage.laundry.data.model.response.CustomerLoginResponse
import com.example.manage.laundry.data.model.response.GetStaffsResponse
import com.example.manage.laundry.data.model.response.LoginResponse
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.RegisterCustomerResponse
import com.example.manage.laundry.data.model.response.RegisterOwnerResponse
import com.example.manage.laundry.data.model.response.RegisterStaffResponse
import com.example.manage.laundry.data.model.response.ShopDetailResponse
import com.example.manage.laundry.data.model.response.ShopOrderResponse
import com.example.manage.laundry.data.model.response.ShopSearchResponse
import com.example.manage.laundry.data.model.response.ShopServiceResponse
import com.example.manage.laundry.data.model.response.StaffLoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType


class ApiService(private val client: HttpClient, private val baseUrl: String) {

    companion object {
        var token: String? = null
    }

    suspend fun test(): ApiResponse<String> = client.get("$baseUrl/hello").body()

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

    suspend fun getStaffs(shopId: Int): ApiResponse<GetStaffsResponse> =
        client.get("$baseUrl/owners/shops/$shopId/staffs").body()

    suspend fun addStaff(
        shopId: Int,
        request: StaffRegisterRequest
    ): ApiResponse<RegisterStaffResponse> =
        client.post("$baseUrl/owners/shops/$shopId/staffs") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun addService(
        shopId: Int,
        request: CreateServiceRequest
    ): ApiResponse<List<ShopServiceResponse>> =
        client.post("$baseUrl/owners/shops/$shopId/services") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun updateService(
        serviceId: Int,
        request: UpdateServiceRequest
    ): ApiResponse<ShopServiceResponse> =
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
        client.get("$baseUrl/staff/orders") {
            addAuthorization()
        }.body()

    suspend fun updateOrderStatus(
        orderId: Int,
        request: UpdateOrderStatusRequest
    ): ApiResponse<Nothing> =
        client.put("$baseUrl/staff/orders/$orderId/status") {
            contentType(ContentType.Application.Json)
            addAuthorization()
            setBody(request)
        }.body()

    suspend fun confirmOrder(
        orderId: Int,
        newPrice: Int? = null,
        staffResponse: String? = null,
    ): ApiResponse<Nothing> =
        client.put("$baseUrl/staff/orders/$orderId/confirm") {
            contentType(ContentType.Application.Json)
            addAuthorization()
            setBody(ConfirmOrderRequest(newPrice, staffResponse))
        }.body()

    suspend fun cancelOrder(
        orderId: Int,
        staffResponse: String? = null,
    ): ApiResponse<Nothing> =
        client.put("$baseUrl/staff/orders/$orderId/cancel") {
            contentType(ContentType.Application.Json)
            addAuthorization()
            setBody(staffResponse?.let { CancelOrderRequest(it) })
        }.body()

    suspend fun loadOrderByStatus(status: Order.Status): ApiResponse<List<OrderResponse>> =
        client.get("$baseUrl/staff/orders/status/${status.name}") {
            addAuthorization()
        }.body()

    suspend fun loadOrderForStaff(): ApiResponse<List<OrderResponse>> =
        client.get("$baseUrl/staff/orders/for-staff") {
            addAuthorization()
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

    suspend fun getOrderHistory(): ApiResponse<List<OrderResponse>> =
        client.get("$baseUrl/customers/orders") {
            addAuthorization()
        }.body()

    suspend fun trackOrder(orderId: Int): ApiResponse<OrderResponse> =
        client.get("$baseUrl/customers/orders/$orderId/track") {
            addAuthorization()
        }.body()

    suspend fun getServices(shopId: Int): ApiResponse<List<ShopServiceResponse>> =
        client.get("$baseUrl/owners/shops/$shopId/services").body()

    suspend fun createOrder(createOrderRequest: CreateOrderRequest): ApiResponse<CreateOrderResponse> =
        client.post("$baseUrl/customers/orders") {
            contentType(ContentType.Application.Json)
            addAuthorization()
            setBody(createOrderRequest)
        }.body()

    suspend fun getShopDetails(shopId: Int): ApiResponse<ShopDetailResponse> =
        client.get("$baseUrl/owners/shops/$shopId").body()

    suspend fun customerConfirmOrder(orderId: Int): ApiResponse<Nothing> =
        client.put("$baseUrl/customers/orders/$orderId/confirm") {
            addAuthorization()
        }.body()

    suspend fun customerCancelOrder(orderId: Int): ApiResponse<Nothing> =
        client.put("$baseUrl/customers/orders/$orderId/cancel") {
            addAuthorization()
        }.body()


    private fun HttpRequestBuilder.addAuthorization() {
        headers.append(HttpHeaders.Authorization, "Bearer $token")
    }


}