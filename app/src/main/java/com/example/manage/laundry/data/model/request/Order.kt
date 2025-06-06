package com.example.manage.laundry.data.model.request

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
class Order {
    enum class Status {
        NEW, /*Khách vừa gửi đồ, chưa tính toán chi phí*/
        PENDING, /*Đã báo giá cho khách, đang chờ khách đồng ý*/
        CANCELED, /*Khách không đồng ý giá và hủy đơn*/
        PROCESSING, /*Khách đã đồng ý, đơn hàng đang được giặt ủi*/
        COMPLETED, /*Đồ đã giặt xong, sẵn sàng trả khách*/
        DELIVERED, /*Khách đã nhận đồ nhưng chưa thanh toán*/
        PAID, /*Khách đã trả tiền (tiền mặt/chuyển khoản/thẻ)*/
        PAID_FAILED; /*Giao dịch thanh toán thất bại (thẻ/ví điện tử)*/

        fun getStringTrackingStepsView(): String {
            return when (this) {
                NEW -> "Mới"
                PENDING -> "Chờ xử lý"
                PROCESSING -> "Đang xử xác nhận"
                COMPLETED -> "Hoàn thành"
                DELIVERED -> "Đã giao"
                PAID -> "Đã thanh toán"
                PAID_FAILED -> "Thanh toán thất bại"
                CANCELED -> "Đã hủy"
            }
        }

        fun getStringStatusChip(): String {
            return when (this) {
                NEW -> "Mới tạo đơn"
                PENDING -> "Đang chờ xác nhận"
                PROCESSING -> "Đang xử lý"
                COMPLETED -> "Hoàn thành"
                DELIVERED -> "Đã giao"
                PAID -> "Đã thanh toán"
                PAID_FAILED -> "Thanh toán thất bại"
                CANCELED -> "Đã hủy"
            }
        }

        @Composable
        fun getColor() = when (this) {
            NEW -> MaterialTheme.colorScheme.tertiary
            PENDING -> MaterialTheme.colorScheme.primary
            CANCELED -> MaterialTheme.colorScheme.error
            PROCESSING -> MaterialTheme.colorScheme.secondary
            COMPLETED -> MaterialTheme.colorScheme.inversePrimary
            DELIVERED -> MaterialTheme.colorScheme.surfaceTint
            PAID -> MaterialTheme.colorScheme.surfaceTint.copy(green = 0.8f)
            PAID_FAILED -> MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
        }

        companion object {
            fun getAvailableStatuses(status: Status): List<Status> {
                return when (status) {
                    NEW -> emptyList()
                    PENDING -> listOf(CANCELED)
                    PROCESSING -> listOf(DELIVERED, CANCELED)
                    DELIVERED -> listOf(COMPLETED, CANCELED)
                    COMPLETED -> emptyList()
                    PAID -> emptyList()
                    PAID_FAILED -> emptyList()
                    CANCELED -> emptyList()
                }
            }
        }

    }
}
