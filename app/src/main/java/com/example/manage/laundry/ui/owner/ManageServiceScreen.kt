package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.data.model.request.CreateServiceRequest
import com.example.manage.laundry.data.model.response.ShopServiceResponse
import com.example.manage.laundry.viewmodel.ServiceState
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import kotlinx.coroutines.launch

@Composable
fun ManageServiceScreen(viewModel: ShopOwnerViewModel) {
    val serviceState by viewModel.serviceState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    var serviceName by remember { mutableStateOf("") }
    var serviceDescription by remember { mutableStateOf("") }
    var servicePrice by remember { mutableStateOf("") }

    LaunchedEffect(serviceState) {
        when (serviceState) {
            is ServiceState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar((serviceState as ServiceState.Error).message)
                }
            }


            is ServiceState.Success -> {
                serviceName = ""
                serviceDescription = ""
                servicePrice = ""
            }

            ServiceState.Loading, ServiceState.Idle -> {}

            ServiceState.Added -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Thêm dịch vụ thành công")
                }
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
            Text("Quản Lý Dịch Vụ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Thêm Dịch Vụ", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            OutlinedTextField(
                value = serviceName,
                onValueChange = { serviceName = it },
                label = { Text("Tên dịch vụ") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = serviceDescription,
                onValueChange = { serviceDescription = it },
                label = { Text("Mô tả dịch vụ") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = servicePrice,
                onValueChange = { servicePrice = it },
                label = { Text("Giá (VND)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = VisualTransformation.None,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val price = servicePrice.toIntOrNull()
                    if (serviceName.isNotBlank() && serviceDescription.isNotBlank() && price != null) {
                        viewModel.addService(
                            shopId = viewModel.loginState.value.getShopId() ?: return@Button,
                            request = CreateServiceRequest(
                                name = serviceName,
                                description = serviceDescription,
                                price = price
                            )
                        )
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vui lòng nhập đầy đủ thông tin hợp lệ!")
                        }
                    }

                    // Ẩn bàn phím sau khi thêm dịch vụ
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm Dịch Vụ")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Danh Sách Dịch Vụ", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        val serviceList = when (serviceState) {
            is ServiceState.Success -> (serviceState as ServiceState.Success).services
            else -> emptyList()
        }

        itemsIndexed(serviceList) { _, service ->
            ServiceItem(service, onDelete = { viewModel.deleteService(service.id) })
        }
    }

    // Hiển thị Snackbar
    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun ServiceItem(service: ShopServiceResponse, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Tên: ${service.name}", fontWeight = FontWeight.Bold)
                Text(text = "Giá: ${service.price} VND", color = Color.Blue)
                Text(text = "Mô tả: ${service.description}", fontSize = 12.sp)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Xóa", tint = Color.Red)
            }
        }
    }
}
