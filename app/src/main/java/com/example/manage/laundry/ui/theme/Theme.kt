package com.example.manage.laundry.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ManageLaundryAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            // Background: Xanh nước biển nhạt, nền tảng nhẹ nhàng
            background = Color(0xFFE0F7FA), // Light Cyan, giống biển trong
            // Surface: Tương tự background nhưng đậm hơn một chút
            surface = Color(0xFFB2EBF2), // Cyan nhạt, tạo độ sâu
            // onSurface: Màu chữ tối trên surface
            onSurface = Color(0xFF1A3C34), // Xanh đậm pha đen, nổi trên surface
            // Primary: Xanh lá cây ngọc lục bảo
            primary = Color(0xFF26A69A), // Teal, kết hợp xanh lá và nước biển
            // onPrimary: Màu tối tương phản với primary
            onPrimary = Color(0xFFFFFFFF), // Trắng, nổi bật và đơn giản
            // Primary Container: Phiên bản nhạt của primary
            primaryContainer = Color(0xFFB2DFDB), // Light Teal, nhẹ nhàng
            // onPrimaryContainer: Màu chữ trên primaryContainer
            onPrimaryContainer = Color(0xFF004D40), // Xanh đậm, dễ đọc
            // Secondary: Xanh nước biển tươi sáng
            secondary = Color(0xFF4FC3F7), // Light Sky Blue, gợi cảm giác biển
            // onSecondary: Màu tối trên secondary
            onSecondary = Color(0xFF01579B), // Dark Blue, tương phản tốt
            // Secondary Container: Phiên bản nhạt của secondary
            secondaryContainer = Color(0xFFB3E5FC), // Light Blue, hài hòa
            // onSecondaryContainer: Màu chữ trên secondaryContainer
            onSecondaryContainer = Color(0xFF0277BD), // Xanh biển đậm nhạt, dễ đọc
            // Tertiary: Xanh lá cây nhẹ để bổ sung
            tertiary = Color(0xFF81C784), // Light Green, tươi mát
            // onTertiary: Màu tối trên tertiary
            onTertiary = Color(0xFF1B5E20), // Xanh lá đậm, tương phản tốt
            // Tertiary Container: Phiên bản nhạt của tertiary
            tertiaryContainer = Color(0xFFC8E6C9), // Very Light Green, nhẹ nhàng
            // onTertiaryContainer: Màu chữ trên tertiaryContainer
            onTertiaryContainer = Color(0xFF2E7D32), // Xanh lá trung, dễ đọc
            // Error: Đỏ hồng nhẹ, nổi bật nhưng không quá lạc lõng
            error = Color(0xFFD81B60), // Pinkish Red, thay vì đỏ chói
            // onError: Màu sáng trên error
            onError = Color(0xFFFFFFFF), // Trắng, tương phản mạnh
            // Error Container: Phiên bản nhạt của error
            errorContainer = Color(0xFFFFCDD2), // Light Pink, hài hòa
            // onErrorContainer: Màu chữ trên errorContainer
            onErrorContainer = Color(0xFFB71C1C), // Đỏ đậm nhạt, dễ đọc
            // Surface Variant: Xanh nước biển pha xám nhẹ
            surfaceVariant = Color(0xFFB2DFDB), // Light Teal, đồng bộ với primaryContainer
            // onSurfaceVariant: Màu chữ trên surfaceVariant
            onSurfaceVariant = Color(0xFF00695C) // Xanh teal đậm, nổi bật
        ),
        typography = Typography,
        content = content
    )
}