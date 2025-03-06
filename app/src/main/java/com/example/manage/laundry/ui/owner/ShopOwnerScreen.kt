package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import kotlinx.coroutines.launch

enum class ShopOwnerTab(
    val title: String,
    val icon: ImageVector,
) {
    STAFF("Nhân viên", Icons.Default.Group),
    SERVICE("Dịch vụ", Icons.Default.Build),
    ORDER("Đơn hàng", Icons.AutoMirrored.Filled.List),
    STATISTICS("Thống kê", Icons.Default.BarChart),
    ACCOUNT("Tài khoản", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopOwnerHomeScreen(viewModel: ShopOwnerViewModel) {
    val horizontalPageState = rememberPagerState(pageCount = { ShopOwnerTab.entries.size })


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
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Quản Lý Tiệm Giặt Ủi",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Đã đăng xuất",
                                    actionLabel = "OK"
                                )
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Đăng xuất",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
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
                    ShopOwnerTab.entries.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = horizontalPageState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    horizontalPageState.animateScrollToPage(
                                        index
                                    )
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
                    0 -> ManageStaffScreen(viewModel)
                    1 -> ManageServiceScreen(viewModel)
                    2 -> ManageOrderScreen(viewModel)
                    else -> throw IllegalStateException("Not implemented")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShopOwnerScaffoldPreview() {
    ManageLaundryAppTheme {
        ShopOwnerHomeScreen(fakeViewModel<ShopOwnerViewModel>())
    }
}