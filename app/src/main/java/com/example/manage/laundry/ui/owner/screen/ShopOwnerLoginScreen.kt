package com.example.manage.laundry.ui.owner.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.BuildConfig
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel.LoginState
import kotlinx.coroutines.delay

@Composable
fun ShopOwnerLoginScreen(
    shopOwnerViewModel: ShopOwnerViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    val loginState by shopOwnerViewModel.loginState.collectAsState()
    var email by remember { mutableStateOf(BuildConfig.DUMMY_SHOP_OWNER_EMAIL) }
    var password by remember { mutableStateOf(BuildConfig.DUMMY_SHOP_OWNER_PASSWORD) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            delay(2000)
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo hoặc Icon
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Shop Owner Icon",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Text(
                text = "Đăng Nhập Chủ Cửa Hàng",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Đăng nhập để quản lý cửa hàng của bạn",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card chứa form đăng nhập
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Email TextField
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Email Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password TextField
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Mật khẩu") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password visibility",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Liên kết Quên mật khẩu
                    TextButton(onClick = { /* TODO: Handle forgot password */ }) {
                        Text(
                            text = "Quên mật khẩu?",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nút đăng nhập
                    Button(
                        onClick = { shopOwnerViewModel.login(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(4.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Đăng Nhập", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Trạng thái loading, lỗi, hoặc thành công
            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = loginState is LoginState.Loading ||
                        loginState is LoginState.Error ||
                        loginState is LoginState.Success,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                when (loginState) {
                    is LoginState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    is LoginState.Error -> {
                        Text(
                            text = "Lỗi: ${(loginState as LoginState.Error).message}",
                            fontSize = 14.sp,
                            color = Color.Red,
                            modifier = Modifier
                                .background(Color(0xFFFFE0E0), RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }

                    is LoginState.Success -> {
                        Text(
                            text = "Chào mừng, ${(loginState as LoginState.Success).data?.name}!",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }

                    LoginState.Idle -> {} // Không hiển thị gì khi ở trạng thái mặc định
                }
            }

            // Nút chuyển sang đăng ký
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Chưa có tài khoản?", fontSize = 14.sp, color = Color.Gray)
                TextButton(onClick = onNavigateToRegister) {
                    Text("Đăng ký ngay", fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopOwnerLoginScreenPreview() {
    ManageLaundryAppTheme {
        ShopOwnerLoginScreen(
            shopOwnerViewModel = fakeViewModel<ShopOwnerViewModel>(),
            onLoginSuccess = { TODO("Not yet implemented") },
            onNavigateToRegister = { TODO("Not yet implemented") }
        )
    }
}
