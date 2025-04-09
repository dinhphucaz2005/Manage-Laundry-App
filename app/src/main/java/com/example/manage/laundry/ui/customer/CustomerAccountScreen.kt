package com.example.manage.laundry.ui.customer

import androidx.compose.runtime.Composable
import com.example.manage.laundry.viewmodel.CustomerViewModel

@Composable
fun CustomerAccountScreen(viewModel: CustomerViewModel) {
    CreateOrderScreen(
        shopId = 0,
        viewModel = viewModel
    )
}
