package com.example.manage.laundry.ui.customer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manage.laundry.BaseActivity
import com.example.manage.laundry.di.fakeViewModel
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
                NavHost(
                    navController = navController,
                    startDestination = CustomerRoute.LOGIN,
                ) {

                    composable(route = CustomerRoute.LOGIN) {
                        CustomerLoginScreen(
                            viewModel = customerViewModel,
                            onLoginSuccess = { navController.navigate(CustomerRoute.HOME) },
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