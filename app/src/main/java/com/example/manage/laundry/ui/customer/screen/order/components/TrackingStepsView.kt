package com.example.manage.laundry.ui.customer.screen.order.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.request.Order

@Composable
fun TrackingStepsView(status: String) {
    // Define the main steps in the order flow
    val mainSteps = listOf(
        Order.Status.NEW,
        Order.Status.PENDING,
        Order.Status.PROCESSING,
        Order.Status.COMPLETED,
        Order.Status.DELIVERED,
        Order.Status.PAID
    )

    // Find the current step index
    val currentStatus = try {
        Order.Status.valueOf(status)
    } catch (e: IllegalArgumentException) {
        Order.Status.NEW // Default to NEW if status is invalid
    }

    // Special handling for CANCELED and PAID_FAILED statuses
    val isErrorState = currentStatus == Order.Status.CANCELED ||
            currentStatus == Order.Status.PAID_FAILED

    // If normal flow, find the current step index
    val currentStepIndex = if (!isErrorState) {
        mainSteps.indexOf(currentStatus).coerceAtLeast(0)
    } else {
        // For error states, find the step before the error
        when (currentStatus) {
            Order.Status.CANCELED -> mainSteps.indexOf(Order.Status.PENDING).coerceAtLeast(0)
            Order.Status.PAID_FAILED -> mainSteps.indexOf(Order.Status.DELIVERED).coerceAtLeast(0)
            else -> 0
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tiến trình đơn hàng",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        if (isErrorState) {
            Text(
                text = when (currentStatus) {
                    Order.Status.CANCELED -> "Đơn hàng đã bị hủy"
                    Order.Status.PAID_FAILED -> "Thanh toán thất bại"
                    else -> "Lỗi không xác định"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        mainSteps.forEachIndexed { index, step ->
            Column {
                StepIndicator(
                    label = step.getStringTrackingStepsView(),
                    isCompleted = if (isErrorState) {
                        index < currentStepIndex
                    } else {
                        index <= currentStepIndex
                    },
                    isActive = index == currentStepIndex,
                )
            }
        }
    }
}