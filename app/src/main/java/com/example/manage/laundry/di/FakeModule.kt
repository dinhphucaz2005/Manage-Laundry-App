package com.example.manage.laundry.di

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.manage.laundry.BuildConfig
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
import com.example.manage.laundry.data.model.response.ShopResponse
import com.example.manage.laundry.data.model.response.ShopSearchResponse
import com.example.manage.laundry.data.model.response.ShopServiceResponse
import com.example.manage.laundry.data.model.response.StaffLoginResponse
import com.example.manage.laundry.data.model.response.UserResponse
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.di.repository.CustomerRepository
import com.example.manage.laundry.di.repository.ShopOwnerRepository
import com.example.manage.laundry.di.repository.StaffRepository
import com.example.manage.laundry.ui.customer.CustomerViewModel
import com.example.manage.laundry.ui.owner.screen.statistic.StatisticsViewModel
import com.example.manage.laundry.ui.staff.StaffViewModel
import com.example.manage.laundry.ui.staff.screen.delivery.DeliveryViewModel
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import io.ktor.client.HttpClient
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

    override suspend fun login(request: CustomerLoginRequest): ApiResponse<CustomerLoginResponse> {
        return if (request.email == "test@gmail.com" && request.password == "test") {
            ApiResponse(
                data = CustomerLoginResponse(
                    "fake_token",
                    "Nguyễn Văn A",
                    request.email,
                    "0987654321"
                )
            )

        } else {
            ApiResponse(
                success = false,
                message = "Invalid email or password"
            )
        }

    }

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
                        4.5
                    )
                )
        )

    override suspend fun getOrderHistory(): ApiResponse<List<OrderResponse>> =
        ApiResponse(data = emptyList())

    override suspend fun trackOrder(orderId: Int): ApiResponse<OrderResponse> =
        ApiResponse(
            data =
                OrderResponse(
                    id = orderId,
                    shopName = "Tiệm Giặt ABC",
                    customerName = "Nguyễn Văn A",
                    estimatePrice = 50000,
                    status = Order.Status.PENDING,
                    totalPrice = null,
                    createdAt = String.now(),
                    items = listOf(
                        OrderResponse.OrderItemResponse(
                            id = 1,
                            name = "Giặt sấy",
                            quantity = 2,
                            price = 25000,
                            totalPrice = 50000
                        )
                    ),
                    specialInstructions = null,
                    updatedAt = String.now()
                )
        )

    override suspend fun createOrder(request: CreateOrderRequest): ApiResponse<CreateOrderResponse> {
        return ApiResponse(
            data =
                CreateOrderResponse(
                    orderId = 1,
                    estimatePrice = 5,
                    status = "sdlk",
                    createdAt = "L",
                )
        )
    }

    override suspend fun getShopDetails(shopId: Int): ApiResponse<ShopDetailResponse> {
        return ApiResponse(
            data = ShopDetailResponse(
                1,
                "Tiệm Giặt ABC",
                "123 Lý Thường Kiệt",
                "Giặt sấy nhanh",
                "08:00",
                services = listOf(
                    ShopDetailResponse.ShopDetailServiceResponse(
                        1,
                        "Giặt sấy",
                        "Giặt và sấy quần áo",
                        50000,
                    ),
                    ShopDetailResponse.ShopDetailServiceResponse(
                        2,
                        "Giặt ủi",
                        "Giặt và ủi quần áo",
                        70000
                    )
                ),
            )
        )
    }
}

private val shopOwnerRepository = object : ShopOwnerRepository {

    private val users: MutableList<UserResponse> = mutableListOf()
    private val shopServices: MutableList<ShopServiceResponse> = mutableListOf()

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
        ApiResponse(
            data = LoginResponse(
                "fake_token", 1, "Nguyễn Văn Owner", request.email,
                shop = ShopResponse(
                    1,
                    "Tiệm Giặt ABC",
                    "123 Lý Thường Kiệt",
                    "08:00",
                    "21:00"
                )
            )
        )

    override suspend fun getStaffs(shopId: Int): ApiResponse<GetStaffsResponse> {
        return ApiResponse(
            data = GetStaffsResponse(
                staffs = listOf(
                    UserResponse(
                        2,
                        "Nguyễn Văn Staff",
                        "nguyenvanstaff@gmail.com",
                        "0987654321",
                        "STAFF"
                    ),
                    UserResponse(
                        3,
                        "Trần Thị Staff",
                        "tranthistaff@gmail.com",
                        "0987654321",
                        "STAFF"
                    ),
                    UserResponse(4, "Lê Văn Staff", "lenvanstaff@gmail.com", "0987654321", "STAFF")
                )
            )
        )
    }

    override suspend fun addStaff(
        shopId: Int,
        request: StaffRegisterRequest
    ): ApiResponse<RegisterStaffResponse> {
        users.add(UserResponse(2, request.name, request.email, request.phone, "STAFF"))
        return ApiResponse(
            data =
                RegisterStaffResponse(
                    shop = ShopResponse(
                        shopId,
                        "Tiệm Giặt ABC",
                        "123 Lý Thường Kiệt",
                        "08:00",
                        "21:00"
                    ),
                    staffs = users
                )
        )

    }

    override suspend fun addService(
        shopId: Int,
        request: CreateServiceRequest
    ): ApiResponse<List<ShopServiceResponse>> {
        shopServices.add(
            ShopServiceResponse(
                1,
                request.name,
                request.description,
                request.price,
                shopId
            )
        )
        return ApiResponse(data = shopServices)
    }


    override suspend fun updateService(
        serviceId: Int,
        request: UpdateServiceRequest
    ): ApiResponse<ShopServiceResponse> =
        ApiResponse(
            data =
                ShopServiceResponse(
                    serviceId,
                    request.name,
                    request.description,
                    request.price,
                    1
                )
        )

    override suspend fun deleteService(serviceId: Int): ApiResponse<Unit> =
        ApiResponse(data = null)

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
                        Order.Status.COMPLETED,
                        String.now()
                    )
                )
        )

    override suspend fun updateOrder(
        orderId: Int,
        request: UpdateOrderRequest
    ): ApiResponse<OrderResponse> =
        ApiResponse(
            data =
                OrderResponse(
                    id = orderId,
                    shopName = "Tiệm Giặt ABC",
                    customerName = "Nguyễn Văn A",
                    estimatePrice = 50000,
                    totalPrice = null,
                    status = Order.Status.COMPLETED,
                    specialInstructions = null,
                    createdAt = String.now(),
                    items = listOf(
                        OrderResponse.OrderItemResponse(
                            id = 1,
                            name = "Giặt sấy",
                            quantity = 2,
                            price = 25000,
                            totalPrice = 50000
                        )
                    ),
                    updatedAt = String.now()
                )
        )

    override suspend fun getServices(shopId: Int): ApiResponse<List<ShopServiceResponse>> {
        return ApiResponse(
            data = listOf(
                ShopServiceResponse(
                    1,
                    "Giặt sấy",
                    "Giặt và sấy quần áo",
                    50000,
                    shopId
                ),
                ShopServiceResponse(
                    2,
                    "Giặt ủi",
                    "Giặt và ủi quần áo",
                    70000,
                    shopId
                )
            )
        )
    }
}

private val staffRepository = object : StaffRepository {
    override suspend fun login(request: StaffLoginRequest): ApiResponse<StaffLoginResponse> =
        ApiResponse(data = StaffLoginResponse("fake_token", 2, 1, "Nguyễn Văn Staff"))

    override suspend fun getOrders(): ApiResponse<List<OrderResponse>> =
        ApiResponse(
            data =
                listOf(
                    OrderResponse(
                        id = 1,
                        shopName = "Tiệm Giặt ABC",
                        createdAt = "Nguyễn Văn A",
                        estimatePrice = 50000,
                        totalPrice = 50000,
                        status =
                            Order.Status.PENDING,

                        specialInstructions = null,
                        customerName = "Nguyen Van B",
                        items = emptyList(),
                        updatedAt = String.now()
                    ),
                )
        )

    override suspend fun updateOrderStatus(
        orderId: Int,
        request: UpdateOrderStatusRequest
    ): ApiResponse<Nothing> =
        ApiResponse(success = true, "Cập nhật trạng thái đơn hàng thành công")
}


private fun String.Companion.now(): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) LocalDate.now().toString() else "2025-03-07"


fun provideFakeCustomerViewModel() = CustomerViewModel(
    repository = customerRepository,
    apiService = ApiService(
        client = HttpClient(),
        baseUrl = ""
    )
)

fun provideFakeShopOwnerViewModel() = ShopOwnerViewModel(shopOwnerRepository)

fun provideFakeStaffViewModel() = StaffViewModel(
    staffRepository = staffRepository, apiService = ApiService(
        client = HttpClient(),
        baseUrl = ""
    )
)

fun provideFakeDeliveryViewModel() = DeliveryViewModel(
    apiService = ApiService(
        client = HttpClient(),
        baseUrl = ""
    )
)

fun provideStatisticsViewModel() = StatisticsViewModel(
    apiService = ApiService(
        client = HttpClient(),
        baseUrl = ""
    )
)

@Composable
inline fun <reified T : ViewModel> fakeViewModel(): T {
    val isPreview = LocalInspectionMode.current
    return if (isPreview || BuildConfig.USE_FAKE_VIEWMODEL) {
        when (T::class) {
            CustomerViewModel::class -> provideFakeCustomerViewModel() as T
            ShopOwnerViewModel::class -> provideFakeShopOwnerViewModel() as T
            StaffViewModel::class -> provideFakeStaffViewModel() as T
            DeliveryViewModel::class -> provideFakeDeliveryViewModel() as T
            StatisticsViewModel::class -> provideStatisticsViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${T::class.simpleName}")
        }
    } else {
        hiltViewModel<T>()
    }

}