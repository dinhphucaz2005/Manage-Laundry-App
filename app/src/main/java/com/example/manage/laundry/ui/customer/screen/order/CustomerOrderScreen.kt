package com.example.manage.laundry.ui.customer.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.customer.CustomerState
import com.example.manage.laundry.ui.customer.CustomerViewModel
import com.example.manage.laundry.ui.customer.screen.order.components.EmptyOrdersView
import com.example.manage.laundry.ui.customer.screen.order.components.OrdersList
import com.example.manage.laundry.ui.customer.screen.order.components.TrackOrderDialog
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme

@Preview
@Composable
private fun CustomerOrderScreenPreview() {
    ManageLaundryAppTheme {
        CustomerOrderScreen(
            customerViewModel = fakeViewModel<CustomerViewModel>()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerOrderScreen(
    customerViewModel: CustomerViewModel
) {
    val orderHistoryState by customerViewModel.orderHistoryState.collectAsState()
    var showTrackOrderDialog by remember { mutableStateOf(false) }
    var selectedOrderId by remember { mutableIntStateOf(-1) }

    LaunchedEffect(key1 = Unit) {
        customerViewModel.getOrderHistory()
    }

    Scaffold { paddingValues ->
        Column {
            TopAppBar(
                title = {
                    Text(
                        "Ứng dụng Giặt Ủi",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = {
                        TODO()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Đăng xuất",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (orderHistoryState) {
                    is CustomerState.OrderHistory.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is CustomerState.OrderHistory.Success -> {
                        val orders =
                            (orderHistoryState as CustomerState.OrderHistory.Success).orders
                        if (orders.isEmpty()) {
                            EmptyOrdersView()
                        } else {
                            OrdersList(
                                orders = orders,
                                onOrderClick = { orderId ->
                                    selectedOrderId = orderId
                                    showTrackOrderDialog = true
                                }
                            )
                        }
                    }

                    is CustomerState.OrderHistory.Error -> {
                        val errorMessage =
                            (orderHistoryState as CustomerState.OrderHistory.Error).message
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Không thể tải danh sách đơn hàng: $errorMessage",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { customerViewModel.getOrderHistory() }) {
                                Text("Thử lại")
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    // Track Order Dialog
    if (showTrackOrderDialog) {
        TrackOrderDialog(
            viewModel = customerViewModel,
            orderId = selectedOrderId,
            onDismiss = { showTrackOrderDialog = false }
        )
    }
}
