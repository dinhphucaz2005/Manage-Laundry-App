package com.example.manage.laundry.ui.customer.screen.order.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.customer.CustomerState
import com.example.manage.laundry.ui.customer.CustomerViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme

@Preview
@Composable
private fun TrackOrderDialogPreview() {
    ManageLaundryAppTheme {
        TrackOrderDialog(
            viewModel = fakeViewModel<CustomerViewModel>(),
            orderId = 1
        ) { }
    }
}

@Composable
fun TrackOrderDialog(
    viewModel: CustomerViewModel,
    orderId: Int,
    onDismiss: () -> Unit
) {
    val trackOrderState by viewModel.trackOrderState.collectAsState()

    LaunchedEffect(key1 = orderId) {
        viewModel.trackOrder(orderId)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Theo dõi đơn hàng #$orderId") },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(300.dp)
            ) {
                when (trackOrderState) {
                    is CustomerState.TrackOrder.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is CustomerState.TrackOrder.Success -> {
                        val orderDetail =
                            (trackOrderState as CustomerState.TrackOrder.Success).order
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                            alpha = 0.5f
                                        )
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = orderDetail.shopName,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Tạm tính:")
                                            Text(
                                                orderDetail.estimatePriceString,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }


                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Tổng tiền:")
                                            Text(
                                                orderDetail.totalPriceString,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }


                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Trạng thái:")
                                            StatusChip(status = orderDetail.status.name)
                                        }


                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Ngày đặt:")
                                            Text(orderDetail.createdAtString)
                                        }


                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Cập nhật:")
                                            Text(orderDetail.updatedAtString)
                                        }

                                        if (!orderDetail.specialInstructions.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            HorizontalDivider()
                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                text = "Yêu cầu đặc biệt:",
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(text = orderDetail.specialInstructions)
                                        }
                                    }
                                }
                            }

                            item { TrackingStepsView(status = orderDetail.status.name) }
                        }
                    }

                    is CustomerState.TrackOrder.Error -> {
                        val errorMessage =
                            (trackOrderState as CustomerState.TrackOrder.Error).message
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Không thể tải thông tin: $errorMessage",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Button(onClick = { viewModel.trackOrder(orderId) }) {
                                Text("Thử lại")
                            }
                        }
                    }

                    else -> {}
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Đóng")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    )
}
