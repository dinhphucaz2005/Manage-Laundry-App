package com.example.manage.laundry.ui.staff.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.data.model.response.OrderResponse
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.staff.StaffViewModel
import com.example.manage.laundry.ui.staff.screen.home.components.OrderCard
import com.example.manage.laundry.ui.staff.screen.home.components.OrderStatusBottomSheet
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import kotlinx.coroutines.launch

@Preview
@Composable
private fun StaffHomeScreenPreview() {
    ManageLaundryAppTheme {
        StaffHomeScreen(staffViewModel = fakeViewModel<StaffViewModel>())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffHomeScreen(
    staffViewModel: StaffViewModel
) {
    val orderState by staffViewModel.orderState.collectAsState()
    val updateOrderState by staffViewModel.updateOrderState.collectAsState()
    var selectedOrder by remember { mutableStateOf<OrderResponse?>(null) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        staffViewModel.getOrders()
    }

    LaunchedEffect(updateOrderState) {
        coroutineScope.launch {
            when (updateOrderState) {
                is StaffViewModel.UpdateOrderState.Error -> (updateOrderState as StaffViewModel.UpdateOrderState.Error).message.let { message ->
                    snackbarHostState.showSnackbar(message)
                }

                else -> {

                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Staff Orders Dashboard") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (orderState) {
                is StaffViewModel.OrderState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }

                is StaffViewModel.OrderState.Error -> {
                    Text(
                        text = (orderState as StaffViewModel.OrderState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                is StaffViewModel.OrderState.Success -> {
                    val orders = (orderState as StaffViewModel.OrderState.Success).orders
                    if (orders.isEmpty()) {
                        Text(
                            text = "No orders found",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            items(orders) { order ->
                                OrderCard(
                                    order = order,
                                    onUpdateClick = {
                                        selectedOrder = order
                                        showBottomSheet = true
                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }

                else -> {
                    // Idle state
                }
            }
        }
    }

    if (showBottomSheet && selectedOrder != null) {
        OrderStatusBottomSheet(
            order = selectedOrder!!,
            onDismiss = { showBottomSheet = false },
            onStatusUpdate = { newStatus ->
                staffViewModel.updateOrderStatus(selectedOrder!!.id, newStatus)
                showBottomSheet = false
            },
            sheetState = sheetState
        )
    }
}



