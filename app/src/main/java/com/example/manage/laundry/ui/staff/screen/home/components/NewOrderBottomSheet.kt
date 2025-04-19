package com.example.manage.laundry.ui.staff.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.response.OrderResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderBottomSheet(
    order: OrderResponse,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onVerifyPrice: (Int, String) -> Unit,
    onCancel: (String) -> Unit
) {
    var selectedAction by remember { mutableStateOf("verify") } // "verify" or "cancel"
    var price by remember { mutableStateOf(order.estimatePriceInt.toString()) }
    var staffResponse by remember { mutableStateOf("") }
    var cancelReason by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Đơn hàng mới #${order.id}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Customer info and items
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Thông tin đơn hàng",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Khách hàng:")
                        Text(order.customerName ?: "Không có thông tin")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Giá dự tính:")
                        Text(
                            order.estimatePriceString,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (!order.specialInstructions.isNullOrBlank()) {
                        Text(
                            text = "Yêu cầu đặc biệt:",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = order.specialInstructions)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = selectedAction == "verify",
                    onClick = { selectedAction = "verify" },
                    label = { Text("Xác nhận giá") },
                    leadingIcon = {
                        if (selectedAction == "verify") {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                )

                FilterChip(
                    selected = selectedAction == "cancel",
                    onClick = { selectedAction = "cancel" },
                    label = { Text("Hủy đơn hàng") },
                    leadingIcon = {
                        if (selectedAction == "cancel") {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dynamic content based on selected action
            when (selectedAction) {
                "verify" -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Giá đã xác nhận") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = staffResponse,
                            onValueChange = { staffResponse = it },
                            label = { Text("Phản hồi của nhân viên (tùy chọn)") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                    }
                }

                "cancel" -> {
                    OutlinedTextField(
                        value = cancelReason,
                        onValueChange = { cancelReason = it },
                        label = { Text("Lý do hủy") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ĐÓNG")
                }

                Button(
                    onClick = {
                        if (selectedAction == "verify") {
                            val priceValue = price.toIntOrNull() ?: order.estimatePriceInt
                            onVerifyPrice(priceValue, staffResponse)
                        } else {
                            onCancel(cancelReason)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = when (selectedAction) {
                        "verify" -> price.isNotBlank()
                        "cancel" -> cancelReason.isNotBlank()
                        else -> false
                    }
                ) {
                    Text(if (selectedAction == "verify") "XÁC NHẬN" else "HỦY ĐƠN")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}