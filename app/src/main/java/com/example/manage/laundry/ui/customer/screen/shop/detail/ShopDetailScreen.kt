package com.example.manage.laundry.ui.customer.screen.shop.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.data.model.request.CreateOrderRequest
import com.example.manage.laundry.data.model.response.ShopDetailResponse
import com.example.manage.laundry.ui.customer.CustomerState
import com.example.manage.laundry.ui.customer.CustomerViewModel
import com.example.manage.laundry.ui.customer.screen.shop.detail.components.CartPreviewSheet
import com.example.manage.laundry.ui.customer.screen.shop.detail.components.OrderBottomSheet
import com.example.manage.laundry.ui.customer.screen.shop.detail.components.ServiceCardWithOrder
import com.example.manage.laundry.ui.customer.screen.shop.detail.components.ShopInfoSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailScreen(
    viewModel: CustomerViewModel,
    shopId: Int,
    onNavigateBack: () -> Unit
) {
    val shopDetailState by viewModel.shopDetailState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedService by remember {
        mutableStateOf<ShopDetailResponse.ShopDetailServiceResponse?>(null)
    }
    var quantity by remember { mutableIntStateOf(1) }
    var specialInstructions by remember { mutableStateOf("") }
    var showCartSheet by remember { mutableStateOf(false) }

    // Access the cart from the viewModel for badge count
    val cartItemCount = remember(viewModel.getCartItemCount()) { viewModel.getCartItemCount() }


    LaunchedEffect(key1 = shopId) {
        viewModel.getShopDetails(shopId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết cửa hàng") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (cartItemCount > 0) {
                                Badge { Text(cartItemCount.toString()) }
                            }
                        }
                    ) {
                        IconButton(onClick = { showCartSheet = true }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Giỏ hàng",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (shopDetailState) {
                is CustomerState.ShopDetail.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is CustomerState.ShopDetail.Success -> {
                    val shopDetail = (shopDetailState as CustomerState.ShopDetail.Success).shop

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            ShopInfoSection(
                                name = shopDetail.name,
                                location = shopDetail.location,
                                openTime = shopDetail.openTime,
                                closeTime = shopDetail.closeTime
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Dịch vụ",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(shopDetail.services) { service ->
                            ServiceCardWithOrder(
                                service = service,
                                onOrderClick = {
                                    selectedService = service
                                    quantity = 1
                                    specialInstructions = ""
                                    showBottomSheet = true
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                is CustomerState.ShopDetail.Error -> {
                    val errorMessage = (shopDetailState as CustomerState.ShopDetail.Error).message
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Không thể tải thông tin: $errorMessage",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.getShopDetails(shopId) }) {
                            Text("Thử lại")
                        }
                    }
                }

                else -> {}
            }
        }
    }

    if (showBottomSheet && selectedService != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            OrderBottomSheet(
                service = selectedService!!,
                quantity = quantity,
                onQuantityChange = { quantity = it },
                specialInstructions = specialInstructions,
                onSpecialInstructionsChange = { specialInstructions = it },
                onAddToCart = {
                    val orderRequest = CreateOrderRequest(
                        shopId = shopId,
                        specialInstructions = specialInstructions.ifEmpty { null },
                        items = listOf(
                            CreateOrderRequest.Item(
                                serviceId = selectedService!!.id,
                                quantity = quantity
                            )
                        )
                    )
                    coroutineScope.launch {
                        val message = viewModel.addToCart(orderRequest)
                        showBottomSheet = false
                        snackbarHostState.showSnackbar(message = message)
                    }
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }

    if (showCartSheet) {
        ModalBottomSheet(
            onDismissRequest = { showCartSheet = false },
            sheetState = bottomSheetState
        ) {
            CartPreviewSheet(
                viewModel = viewModel,
                onDismiss = { showCartSheet = false },
                onCheckout = {
                    // Navigate to checkout or process the order
                    viewModel.createOrder()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Đang xử lý đơn hàng...")
                    }
                },
                onClearCart = {
                    viewModel.clearCart()
                    showCartSheet = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Đã xóa giỏ hàng")
                    }
                }
            )
        }
    }

}
