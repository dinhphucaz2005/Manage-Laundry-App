package com.example.manage.laundry.ui.customer.screen.order.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme

@Preview
@Composable
private fun StepIndicatorPreview() {
    ManageLaundryAppTheme {
        StepIndicator(
            label = "Đã thanh toán",
            isCompleted = true,
            isActive = false,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun StepIndicatorPreviewUnActive() {
    ManageLaundryAppTheme {
        StepIndicator(
            label = "Đã thanh toán",
            isCompleted = false,
            isActive = true,
            modifier = Modifier
        )
    }
}


@Composable
fun StepIndicator(
    label: String,
    isCompleted: Boolean,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = when {
                isActive -> MaterialTheme.colorScheme.primary
                isCompleted -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            border = BorderStroke(1.dp, Color.White),
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = if (isActive || isCompleted)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}