package com.example.manage.laundry.ui.staff.screen.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.model.request.Order
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DeliveryUiState(
    val orders: List<OrderResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val apiService: ApiService,
) : ViewModel() {

    fun reset() {
        _uiState.update {
            it.copy(
                orders = emptyList(),
                isLoading = false,
                error = null
            )
        }
    }


    private val _uiState = MutableStateFlow(DeliveryUiState())
    val uiState: StateFlow<DeliveryUiState> = _uiState.asStateFlow()

    fun loadCompletedOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Fetch orders in COMPLETED state that are ready for delivery
                val response = apiService.loadOrderByStatus(Order.Status.COMPLETED)

                if (response.success) {
                    val completedOrders = response.data

                    // Update the UI state with the completed orders
                    _uiState.update {
                        it.copy(
                            orders = completedOrders ?: emptyList(),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            error = "Không thể tải danh sách đơn hàng",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Không thể tải danh sách đơn hàng",
                        isLoading = false
                    )
                }
            }
        }
    }


}