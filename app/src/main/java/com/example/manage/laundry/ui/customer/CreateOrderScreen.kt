package com.example.manage.laundry.ui.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.data.model.request.CreateOrderRequest
import com.example.manage.laundry.viewmodel.CustomerViewModel


@Composable
fun CreateOrderScreen(shopId: Int, viewModel: CustomerViewModel) {
    var specialInstructions by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf<CreateOrderRequest.Item>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Create Order for Shop #$shopId", style = MaterialTheme.typography.titleLarge)

        // Input for special instructions
        OutlinedTextField(
            value = specialInstructions,
            onValueChange = { specialInstructions = it },
            label = { Text("Special Instructions") },
            modifier = Modifier.fillMaxWidth()
        )

        // Add items (example: hardcoded for simplicity)
        Button(onClick = {
            items = items + CreateOrderRequest.Item(serviceId = 1, quantity = 1)
        }) {
            Text("Add Item")
        }

        // Submit order
        Button(onClick = {
            viewModel.createOrder(CreateOrderRequest(shopId, specialInstructions, items))
        }) {
            Text("Submit Order")
        }
    }
}