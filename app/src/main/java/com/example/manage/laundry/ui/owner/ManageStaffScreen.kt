package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.data.model.request.StaffRegisterRequest
import com.example.manage.laundry.data.model.response.UserResponse
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel.StaffState
import kotlinx.coroutines.launch

@Composable
fun ManageStaffScreen(viewModel: ShopOwnerViewModel) {
    val staffState by viewModel.staffState.collectAsState()

    var staffName by remember { mutableStateOf("") }
    var staffEmail by remember { mutableStateOf("") }
    var staffPassword by remember { mutableStateOf("") }
    var staffPhone by remember { mutableStateOf("") }

    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current // 🔹 Thêm controller bàn phím

    LaunchedEffect(staffState) {
        when (staffState) {
            is StaffState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar((staffState as StaffState.Error).message)
                }
            }

            StaffState.Added -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Thêm nhân viên thành công")
                }
            }

            StaffState.Idle, StaffState.Loading -> {}
            is StaffState.Success -> {
                staffName = ""
                staffEmail = ""
                staffPassword = ""
                staffPhone = ""
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Quản Lý Nhân Viên", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Thêm Nhân Viên", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            OutlinedTextField(
                value = staffName,
                onValueChange = { staffName = it },
                label = { Text("Tên") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = staffEmail,
                onValueChange = { staffEmail = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = staffPassword,
                onValueChange = { staffPassword = it },
                label = { Text("Mật khẩu") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            OutlinedTextField(
                value = staffPhone,
                onValueChange = { staffPhone = it },
                label = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.addStaff(
                        StaffRegisterRequest(staffName, staffEmail, staffPassword, staffPhone)
                    )
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm Nhân Viên")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Danh Sách Nhân Viên", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        val staffList = when (staffState) {
            is StaffState.Success -> (staffState as StaffState.Success).staffs
            else -> emptyList()
        }

        itemsIndexed(staffList) { _, staff ->
            StaffItem(staff)
        }
    }

    // Hiển thị Snackbar
    SnackbarHost(hostState = snackbarHostState)
}


@Composable
fun StaffItem(staff: UserResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Tên: ${staff.name}", fontWeight = FontWeight.Bold)
            Text(text = "Email: ${staff.email}")
            Text(text = "Số điện thoại: ${staff.phone}")
        }
    }
}
