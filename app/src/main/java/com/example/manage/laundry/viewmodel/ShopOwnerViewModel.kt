package com.example.manage.laundry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.*
import com.example.manage.laundry.data.model.response.*
import com.example.manage.laundry.di.repository.ShopOwnerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopOwnerViewModel @Inject constructor(
    private val repository: ShopOwnerRepository
) : ViewModel() {

    // 🟢 Trạng thái đăng nhập
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = repository.login(OwnerLoginRequest(email, password))
                if (response.success) {
                    _loginState.value = LoginState.Success(response.data)
                    // get staffs and services
                    response.data?.shop?.id?.let {
                        getStaffs()
                        getServices()
                    }
                } else {
                    _loginState.value = LoginState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Lỗi không xác định")
            }
            _loginState.value
        }
    }

    // 🟢 Trạng thái đăng ký
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun register(request: ShopRegisterRequest) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = repository.register(request)
                if (response.success) {
                    _registerState.value = RegisterState.Success(response.data)
                } else {
                    _registerState.value = RegisterState.Error(response.message)
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    // 🟢 Trạng thái nhân viên
    private val _staffState = MutableStateFlow<StaffState>(StaffState.Idle)
    val staffState: StateFlow<StaffState> = _staffState.asStateFlow()

    fun getStaffs() {
        viewModelScope.launch {
            _staffState.value = StaffState.Loading
            try {
                val shopId = loginState.value.getShopId() ?: return@launch
                val response = repository.getStaffs(shopId)
                if (response.success) {
                    _staffState.value = StaffState.Success(response.data?.staffs ?: emptyList())
                } else {
                    _staffState.value = StaffState.Error(response.message)
                }
            } catch (e: Exception) {
                _staffState.value = StaffState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun addStaff(request: StaffRegisterRequest) {
        viewModelScope.launch {
            _staffState.value = StaffState.Loading
            try {
                val shopId = loginState.value.getShopId() ?: return@launch
                val response = repository.addStaff(shopId, request)
                if (response.success) {
                    _staffState.value = StaffState.Added
                    _staffState.value = StaffState.Success(response.data?.staffs ?: emptyList())
                } else {
                    _staffState.value = StaffState.Error(response.message)
                }
            } catch (e: Exception) {
                _staffState.value = StaffState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    // 🟢 Trạng thái dịch vụ
    private val _serviceState = MutableStateFlow<ServiceState>(ServiceState.Idle)
    val serviceState: StateFlow<ServiceState> = _serviceState.asStateFlow()

    fun getServices() {
        viewModelScope.launch {
            _serviceState.value = ServiceState.Loading
            try {
                val shopId = loginState.value.getShopId() ?: return@launch
                val response = repository.getServices(shopId)
                if (response.success) {
                    _serviceState.value = ServiceState.Success(response.data ?: emptyList())
                } else {
                    _serviceState.value = ServiceState.Error(response.message)
                }
            } catch (e: Exception) {
                _serviceState.value = ServiceState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun addService(shopId: Int, request: CreateServiceRequest) {
        viewModelScope.launch {
            _serviceState.value = ServiceState.Loading
            try {
                val response = repository.addService(shopId, request)
                if (response.success) {
                    _serviceState.value = ServiceState.Added
                    _serviceState.value = ServiceState.Success(response.data ?: emptyList())
                } else {
                    _serviceState.value = ServiceState.Error(response.message)
                }
            } catch (e: Exception) {
                _serviceState.value = ServiceState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun updateService(serviceId: Int, request: UpdateServiceRequest) {
        viewModelScope.launch {
            _serviceState.value = ServiceState.Loading
            try {
                val response = repository.updateService(serviceId, request)
                if (response.success) {
                    _serviceState.value = ServiceState.Success(
                        _serviceState.value.let { current ->
                            if (current is ServiceState.Success) {
                                current.services.map {
                                    if (it.id == serviceId) response.data ?: it else it
                                }
                            } else emptyList()
                        }
                    )
                } else {
                    _serviceState.value = ServiceState.Error(response.message)
                }
            } catch (e: Exception) {
                _serviceState.value = ServiceState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun deleteService(serviceId: Int) {
        viewModelScope.launch {
            _serviceState.value = ServiceState.Loading
            try {
                val response = repository.deleteService(serviceId)
                if (response.success) {
                    _serviceState.value = ServiceState.Success(
                        _serviceState.value.let { current ->
                            if (current is ServiceState.Success) {
                                current.services.filter { it.id != serviceId }
                            } else emptyList()
                        }
                    )
                } else {
                    _serviceState.value = ServiceState.Error(response.message)
                }
            } catch (e: Exception) {
                _serviceState.value = ServiceState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}

// 🟢 Trạng thái cho từng chức năng

sealed class LoginState {
    fun getShopId(): Int? {
        return when (this) {
            is Success -> this.data?.shop?.id
            else -> null
        }
    }

    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginResponse?) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val data: RegisterOwnerResponse?) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class StaffState {
    object Idle : StaffState()
    object Loading : StaffState()
    object Added : StaffState()
    data class Success(val staffs: List<UserResponse>) : StaffState()
    data class Error(val message: String) : StaffState()
}

sealed class ServiceState {
    object Idle : ServiceState()
    object Loading : ServiceState()
    object Added : ServiceState()
    data class Success(val services: List<ShopServiceResponse>) : ServiceState()
    data class Error(val message: String) : ServiceState()
}
