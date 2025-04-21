package com.example.manage.laundry.ui.customer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manage.laundry.BaseActivity
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.service.AppNotificationService
import com.example.manage.laundry.ui.customer.navigation.CustomerRoute
import com.example.manage.laundry.ui.customer.screen.auth.CustomerLoginScreen
import com.example.manage.laundry.ui.customer.screen.shop.detail.ShopDetailScreen
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageLaundryAppTheme {
                val navController = rememberNavController()
                val customerViewModel = fakeViewModel<CustomerViewModel>()

                val snackbarHostState = LocalSnackbarHostState.current

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
                                                    Text(
                                                        label,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                }
                                            }
                                        }
                                    ) {
                                        Text(data.visuals.message)
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = CustomerRoute.LOGIN,
                            Modifier
                                .padding(innerPadding)
                                .statusBarsPadding()
                                .navigationBarsPadding()
                        ) {

                            composable(route = CustomerRoute.LOGIN) {
                                CustomerLoginScreen(
                                    viewModel = customerViewModel,
                                    onLoginSuccess = { customerId ->
                                        navController.navigate(CustomerRoute.HOME) {
                                            popUpTo(CustomerRoute.LOGIN) {
                                                inclusive = true
                                            }
                                        }
                                        startNotificationService(customerId)
                                    },
                                    onRegisterRequest = {}
                                )
                            }

                            composable(route = CustomerRoute.HOME) {
                                CustomerScreen(
                                    customerViewModel = customerViewModel,
                                    onNavigateToShopDetail = { shopId ->
                                        navController.navigate(CustomerRoute.shopDetail(shopId))
                                    }
                                )
                            }

                            composable(
                                route = CustomerRoute.SHOP_DETAIL,
                                arguments = listOf(navArgument("shopId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val shopId = backStackEntry.arguments?.getInt("shopId") ?: -1
                                ShopDetailScreen(
                                    viewModel = customerViewModel,
                                    shopId = shopId,
                                    onNavigateBack = navController::popBackStack
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    private fun startNotificationService(customerId: Int) {
        val intent = Intent(this, AppNotificationService::class.java).apply {
            action = AppNotificationService.ACTION_START_SERVICE
            putExtra(AppNotificationService.EXTRA_CUSTOMER_ID, customerId)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}