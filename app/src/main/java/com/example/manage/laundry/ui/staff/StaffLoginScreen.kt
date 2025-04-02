package com.example.manage.laundry.ui.staff

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.BuildConfig
import com.example.manage.laundry.viewmodel.StaffViewModel
import kotlinx.coroutines.delay

@Composable
fun StaffLoginScreen(
    viewModel: StaffViewModel,
    onLoginSuccess: () -> Unit,
) {
    val loginState by viewModel.loginState.collectAsState()
    var email by remember { mutableStateOf(BuildConfig.DUMMY_STAFF_EMAIL) }
    var password by remember { mutableStateOf(BuildConfig.DUMMY_STAFF_PASSWORD) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (loginState is StaffViewModel.LoginState.Success) {
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
            // Logo or Icon
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Staff Icon",
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
                text = "Đăng Nhập Nhân Viên",
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

            // Card containing login form
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

                    // Forgot password link
                    TextButton(onClick = { /* TODO: Handle forgot password */ }) {
                        Text(
                            text = "Quên mật khẩu?",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login button
                    Button(
                        onClick = { viewModel.login(email, password) },
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

            // Loading, error, or success state
            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = loginState is StaffViewModel.LoginState.Loading ||
                        loginState is StaffViewModel.LoginState.Error ||
                        loginState is StaffViewModel.LoginState.Success,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                when (loginState) {
                    is StaffViewModel.LoginState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    is StaffViewModel.LoginState.Error -> {
                        Text(
                            text = "Lỗi: ${(loginState as StaffViewModel.LoginState.Error).message}",
                            fontSize = 14.sp,
                            color = Color.Red,
                            modifier = Modifier
                                .background(Color(0xFFFFE0E0), RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }

                    is StaffViewModel.LoginState.Success -> {
                        Text(
                            text = "Chào mừng, ${(loginState as StaffViewModel.LoginState.Success).data?.name}!",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }

                    StaffViewModel.LoginState.Idle -> {} // Do not display anything in the default state
                }
            }

            // Navigate to register button
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Chưa có tài khoản?", fontSize = 14.sp, color = Color.Gray)
                TextButton(onClick = { /* TODO: Navigate to Register */ }) {
                    Text("Đăng ký ngay", fontSize = 14.sp)
                }
            }
        }
    }
}