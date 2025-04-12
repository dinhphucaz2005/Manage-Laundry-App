package com.example.manage.laundry.ui.customer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.customer.screen.CustomerHomeContent
import com.example.manage.laundry.ui.customer.screen.order.CustomerOrderScreen
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import kotlinx.coroutines.launch

// Định nghĩa các tab trong màn hình khách hàng
enum class CustomerTab(
    val title: String,
    val icon: ImageVector,
) {
    HOME("Trang chủ", Icons.Default.Home),
    ORDER("Đơn hàng", Icons.Default.ShoppingCart),
    FAVORITE("Yêu thích", Icons.Default.Favorite),
    HISTORY("Lịch sử", Icons.Default.History),
    ACCOUNT("Tài khoản", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(
    customerViewModel: CustomerViewModel,
    onNavigateToShopDetail: (Int) -> Unit,
) {
    val horizontalPageState = rememberPagerState(pageCount = { CustomerTab.entries.size })
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            action = {
                                data.visuals.actionLabel?.let { label ->
                                    TextButton(onClick = { data.dismiss() }) {
                                        Text(label, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        ) {
                            Text(data.visuals.message)
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    tonalElevation = 8.dp
                ) {
                    CustomerTab.entries.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = horizontalPageState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    horizontalPageState.animateScrollToPage(index)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = if (horizontalPageState.currentPage == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            label = {
                                Text(
                                    item.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (horizontalPageState.currentPage == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            alwaysShowLabel = true,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                state = horizontalPageState
            ) { page: Int ->
                when (page) {
                    0 -> CustomerHomeContent(
                        customerViewModel = customerViewModel,
                        onNavigateToShopDetail = onNavigateToShopDetail
                    )

                    1 -> CustomerOrderScreen(customerViewModel = customerViewModel)

                    2 -> Box { Text("PLACE HOLDER", modifier = Modifier.align(Alignment.Center)) }
                    3 -> Box { Text("PLACE HOLDER", modifier = Modifier.align(Alignment.Center)) }
                    4 -> Box { Text("PLACE HOLDER", modifier = Modifier.align(Alignment.Center)) }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CustomerHomeScreenPreview() {
    ManageLaundryAppTheme {
        CustomerScreen(
            customerViewModel = fakeViewModel(),
            onNavigateToShopDetail = {}
        )
    }
}
