package com.example.manage.laundry.ui.customer.screen.order.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TrackingStepsView(status: String) {
    val steps = listOf("PENDING", "PROCESSING", "READY", "COMPLETED")
    val currentStep = steps.indexOf(status).coerceAtLeast(0)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Tiến trình đơn hàng",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            steps.forEachIndexed { index, step ->
                StepIndicator(
                    label = when (step) {
                        "PENDING" -> "Chờ xử lý"
                        "PROCESSING" -> "Đang xử lý"
                        "READY" -> "Sẵn sàng"
                        "COMPLETED" -> "Hoàn thành"
                        else -> step
                    },
                    isCompleted = index <= currentStep,
                    isActive = index == currentStep,
                    modifier = Modifier.weight(1f)
                )

                if (index < steps.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier
                            .width(16.dp)
                            .padding(vertical = 12.dp),
                        color = if (index < currentStep)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}
