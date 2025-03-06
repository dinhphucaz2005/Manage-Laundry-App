package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import kotlinx.coroutines.launch

@Composable
fun ManageStaffScreen(viewModel: ShopOwnerViewModel) {
    var staffName by remember { mutableStateOf("") }
    var staffEmail by remember { mutableStateOf("") }
    var staffPassword by remember { mutableStateOf("") }
    var staffPhone by remember { mutableStateOf("") }
    val shopId = 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Thêm Nhân Viên", fontSize = 20.sp, fontWeight = FontWeight.Bold)

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
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = staffPhone,
            onValueChange = { staffPhone = it },
            label = { Text("Số điện thoại") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val localSnackbarHostState = LocalSnackbarHostState.current
        val coroutineScope = rememberCoroutineScope()

        Button(
            onClick = {

                coroutineScope.launch {
                    localSnackbarHostState.showSnackbar("Đã thêm nhân viên!")
                }


                viewModel.addStaff(
                    shopId,
                    staffName,
                    staffEmail,
                    staffPassword,
                    staffPhone
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Thêm Nhân Viên")
        }
    }
}
