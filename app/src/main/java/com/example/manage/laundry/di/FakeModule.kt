package com.example.manage.laundry.di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
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
import com.example.manage.laundry.data.model.response.CustomerLoginResponse
import com.example.manage.laundry.data.model.response.LoginResponse
import com.example.manage.laundry.data.model.response.OrderHistoryResponse
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.model.response.RegisterCustomerResponse
import com.example.manage.laundry.data.model.response.RegisterOwnerResponse
import com.example.manage.laundry.data.model.response.RegisterStaffResponse
import com.example.manage.laundry.data.model.response.ShopOrderResponse
import com.example.manage.laundry.data.model.response.ShopResponse
import com.example.manage.laundry.data.model.response.ShopSearchResponse
import com.example.manage.laundry.data.model.response.ShopServiceResponse
import com.example.manage.laundry.data.model.response.StaffLoginResponse
import com.example.manage.laundry.data.model.response.TrackOrderResponse
import com.example.manage.laundry.data.model.response.UserResponse
import com.example.manage.laundry.di.repository.CustomerRepository
import com.example.manage.laundry.di.repository.ShopOwnerRepository
import com.example.manage.laundry.di.repository.StaffRepository
import com.example.manage.laundry.viewmodel.CustomerViewModel
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import com.example.manage.laundry.viewmodel.StaffViewModel
import java.time.LocalDate

private val customerRepository = object : CustomerRepository {
    override suspend fun register(request: CustomerRegisterRequest): ApiResponse<RegisterCustomerResponse> =
        ApiResponse(
            data = RegisterCustomerResponse(
                1,
                1,
                request.name,
                request.email,
                request.phone
            )
        )

    override suspend fun login(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse> =
        ApiResponse(
            data =
            CustomerLoginResponse(
                "fake_token",
                "Nguyễn Văn A",
                request.email,
                "0987654321"
            )
        )

    override suspend fun searchShops(): ApiResponse<List<ShopSearchResponse>> =
        ApiResponse(
            data =
            listOf(
                ShopSearchResponse(
                    1,
                    "Tiệm Giặt ABC",
                    "123 Lý Thường Kiệt",
                    "Giặt sấy nhanh",
                    "08:00",
                    "21:00",
                    4.5
                ),
                ShopSearchResponse(
                    2,
                    "Tiệm Giặt XYZ",
                    "456 Nguyễn Trãi",
                    "Giặt ủi cao cấp",
                    "07:00",
                    "22:00",
                    4.0
                )
            )
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOrderHistory(): ApiResponse<List<OrderHistoryResponse>> =
        ApiResponse(
            data =
            listOf(
                OrderHistoryResponse(
                    1,
                    "Tiệm Giặt ABC",
                    50000,
                    "COMPLETED",
                    String.now(),
                    String.now()
                ),
                OrderHistoryResponse(
                    2,
                    "Tiệm Giặt XYZ",
                    70000,
                    "PENDING",
                    String.now(),
                    String.now()
                )
            )
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun trackOrder(orderId: Int): ApiResponse<TrackOrderResponse> =
        ApiResponse(
            data =
            TrackOrderResponse(
                orderId,
                "Tiệm Giặt ABC",
                Order.Status.COMPLETED,
                50000,
                "Giao trước 6h",
                String.now(),
                String.now()
            )
        )
}

private val shopOwnerRepository = object : ShopOwnerRepository {
    override suspend fun register(request: ShopRegisterRequest): ApiResponse<RegisterOwnerResponse> =
        ApiResponse(
            data =
            RegisterOwnerResponse(
                UserResponse(1, request.ownerName, request.email, request.phone, "OWNER"),
                ShopResponse(
                    1,
                    request.shopName,
                    request.address,
                    request.openTime,
                    request.closeTime
                )
            )
        )

    override suspend fun login(request: OwnerLoginRequest): ApiResponse<LoginResponse> =
        ApiResponse(data = LoginResponse("fake_token", 1, "Nguyễn Văn Owner", request.email))

    override suspend fun addStaff(
        shopId: Int,
        request: StaffRegisterRequest
    ): ApiResponse<RegisterStaffResponse> =
        ApiResponse(
            data =
            RegisterStaffResponse(
                UserResponse(2, request.name, request.email, request.phone, "STAFF"),
                ShopResponse(shopId, "Tiệm Giặt ABC", "123 Lý Thường Kiệt", "08:00", "21:00")
            )
        )

    override suspend fun addService(
        shopId: Int,
        request: CreateServiceRequest
    ): ApiResponse<ShopServiceResponse> =
        ApiResponse(
            data =
            ShopServiceResponse(1, request.name, request.description, request.price, shopId)
        )

    override suspend fun updateService(
        serviceId: Int,
        request: UpdateServiceRequest
    ): ApiResponse<ShopServiceResponse> =
        ApiResponse(
            data =
            ShopServiceResponse(serviceId, request.name, request.description, request.price, 1)
        )

    override suspend fun deleteService(serviceId: Int): ApiResponse<Unit> =
        ApiResponse(data = null)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getShopOrders(shopId: Int): ApiResponse<List<ShopOrderResponse>> =
        ApiResponse(
            data =
            listOf(
                ShopOrderResponse(
                    1,
                    "Nguyễn Văn A",
                    50000,
                    Order.Status.PENDING,
                    String.now()
                ),
                ShopOrderResponse(
                    2,
                    "Trần Thị B",
                    70000,
                    Order.Status.CONFIRMED,
                    String.now()
                )
            )
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateOrder(
        orderId: Int,
        request: UpdateOrderRequest
    ): ApiResponse<OrderResponse> =
        ApiResponse(
            data =
            OrderResponse(
                orderId,
                "Tiệm Giặt ABC",
                "Nguyễn Văn A",
                50000,
                request.status,
                request.specialInstructions,
                String.now()
            )
        )
}

private val staffRepository = object : StaffRepository {
    override suspend fun login(request: StaffLoginRequest): ApiResponse<StaffLoginResponse> =
        ApiResponse(data = StaffLoginResponse("fake_token", 2, 1, "Nguyễn Văn Staff"))

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOrders(): ApiResponse<List<OrderResponse>> =
        ApiResponse(
            data =
            listOf(
                OrderResponse(
                    1,
                    "Tiệm Giặt ABC",
                    "Nguyễn Văn A",
                    50000,
                    Order.Status.PENDING,
                    null,
                    String.now()
                ),
                OrderResponse(
                    2,
                    "Tiệm Giặt ABC",
                    "Trần Thị B",
                    70000,
                    Order.Status.PROCESSING,
                    "Gấp gáp",
                    String.now()
                )
            )
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateOrderStatus(
        orderId: Int,
        request: UpdateOrderStatusRequest
    ): ApiResponse<OrderResponse> =
        ApiResponse(
            data = OrderResponse(
                orderId,
                "Tiệm Giặt ABC",
                "Nguyễn Văn A",
                50000,
                Order.Status.valueOf(request.status),
                null,
                String.now()
            )
        )
}


@RequiresApi(Build.VERSION_CODES.O)
private fun String.Companion.now(): String {
    return LocalDate.now().toString()
}


fun provideFakeCustomerViewModel() = CustomerViewModel(customerRepository)

fun provideFakeShopOwnerViewModel() = ShopOwnerViewModel(shopOwnerRepository)

fun provideFakeStaffViewModel() = StaffViewModel(staffRepository)


@Composable
inline fun <reified T : ViewModel> fakeViewModel(): T {
//    val isDebug = LocalInspectionMode.current
    val isDebug = true
    return if (isDebug) {
        when (T::class) {
            CustomerViewModel::class -> provideFakeCustomerViewModel() as T
            ShopOwnerViewModel::class -> provideFakeShopOwnerViewModel() as T
            StaffViewModel::class -> provideFakeStaffViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${T::class.simpleName}")
        }
    } else {
        hiltViewModel<T>()
    }

}