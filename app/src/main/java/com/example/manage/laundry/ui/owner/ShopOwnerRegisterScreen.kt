package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.data.model.request.ShopRegisterRequest
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.RegisterState
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import kotlinx.coroutines.delay

@Composable
fun ShopOwnerRegisterScreen(
    viewModel: ShopOwnerViewModel,
    onRegisterSuccess: () -> Unit = {}
) {
    val registerState by viewModel.registerState.collectAsState()

    var ownerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var shopName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var openTime by remember { mutableStateOf("") }
    var closeTime by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Chờ 2 giây sau khi đăng ký thành công rồi chuyển trang
    LaunchedEffect(registerState) {
        if (registerState is RegisterState.Success) {
            delay(2000)
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Đăng Ký Chủ Cửa Hàng",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = ownerName,
                onValueChange = { ownerName = it },
                label = { Text("Tên Chủ Cửa Hàng") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = shopName,
                onValueChange = { shopName = it },
                label = { Text("Tên Cửa Hàng") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Địa chỉ") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = openTime,
                onValueChange = { openTime = it },
                label = { Text("Giờ mở cửa") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = closeTime,
                onValueChange = { closeTime = it },
                label = { Text("Giờ đóng cửa") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.register(
                        ShopRegisterRequest(
                            ownerName,
                            email,
                            password,
                            phone,
                            shopName,
                            address,
                            openTime,
                            closeTime
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = registerState !is RegisterState.Loading // Không cho click khi đang load
            ) {
                Text("Đăng Ký")
            }

            when (registerState) {
                is RegisterState.Loading -> {
                    CircularProgressIndicator()
                }

                is RegisterState.Error -> {
                    Text(
                        text = "Lỗi: ${(registerState as RegisterState.Error).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                is RegisterState.Success -> {
                    Text(
                        text = "Đăng ký thành công! Chuyển hướng...",
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                }

                RegisterState.Idle -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopOwnerRegisterScreenPreview() {
    ManageLaundryAppTheme {
        ShopOwnerRegisterScreen(viewModel = fakeViewModel<ShopOwnerViewModel>())
    }
}