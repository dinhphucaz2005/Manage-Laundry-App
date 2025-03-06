package com.example.manage.laundry.ui.owner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel

@Preview
@Composable
private fun ShopOwnerScaffoldPreview() {
    ManageLaundryAppTheme {
        ShopOwnerHomeScreen(fakeViewModel<ShopOwnerViewModel>())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopOwnerHomeScreen(viewModel: ShopOwnerViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf("Nhân viên", "Dịch vụ", "Đơn hàng")

    val bottomBarNavigationIcons = listOf(
        Icons.Default.Group,
        Icons.Default.Build,
        Icons.AutoMirrored.Filled.List
    )

    val snackbarHostState = remember { SnackbarHostState() }
    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Quản Lý Tiệm Giặt Ủi") }
                )
            },
            bottomBar = {
                NavigationBar {
                    tabs.forEachIndexed { index, title ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = {
                                Icon(
                                    imageVector = bottomBarNavigationIcons[index],
                                    contentDescription = null
                                )
                            },
                            label = { Text(title) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedTab) {
                    0 -> ManageStaffScreen(viewModel)
                    1 -> ManageServiceScreen(viewModel)
                    2 -> ManageOrderScreen(viewModel)
                }
            }
        }
    }
}




