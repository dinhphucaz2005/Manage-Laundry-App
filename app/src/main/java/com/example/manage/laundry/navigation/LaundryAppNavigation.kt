package com.example.manage.laundry.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.customer.CustomerLoginScreen
import com.example.manage.laundry.ui.owner.ShopOwnerLoginScreen
import com.example.manage.laundry.ui.owner.ShopOwnerHomeScreen
import com.example.manage.laundry.ui.screens.LoginSelectionScreen
import com.example.manage.laundry.ui.screens.SplashScreen
import com.example.manage.laundry.ui.staff.StaffLoginScreen
import com.example.manage.laundry.viewmodel.CustomerViewModel
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import com.example.manage.laundry.viewmodel.StaffViewModel

@Composable
fun LaundryAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LaundryAppScreen.Splash.route,
        modifier = modifier

    ) {

        composable(LaundryAppScreen.LoginSelection.route) {
            LoginSelectionScreen(
                onShopOwnerLoginRequest = { navController.navigate(LaundryAppScreen.ShopOwnerLogin.route) },
                onCustomerLoginRequest = { navController.navigate(LaundryAppScreen.CustomerLogin.route) },
                onStaffLoginRequest = { navController.navigate(LaundryAppScreen.StaffLogin.route) }
            )
        }

        composable(LaundryAppScreen.Splash.route) {
            SplashScreen(onNavigate = {
                navController.navigate(LaundryAppScreen.LoginSelection.route) {
                    popUpTo(LaundryAppScreen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(LaundryAppScreen.CustomerLogin.route) {
            CustomerLoginScreen(viewModel = fakeViewModel<CustomerViewModel>())
        }

        composable(LaundryAppScreen.ShopOwnerLogin.route) {
            ShopOwnerLoginScreen(viewModel = fakeViewModel<ShopOwnerViewModel>(),
                onLoginSuccess = {
                    navController.navigate(LaundryAppScreen.ShopOwnerHome.route) {
                        popUpTo(LaundryAppScreen.ShopOwnerLogin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(LaundryAppScreen.StaffLogin.route) {
            StaffLoginScreen(viewModel = fakeViewModel<StaffViewModel>())
        }

        composable(LaundryAppScreen.ShopOwnerHome.route) {
            ShopOwnerHomeScreen(viewModel = fakeViewModel<ShopOwnerViewModel>())
        }

        composable(LaundryAppScreen.CustomerHome.route) {
            TODO("Customer Home Screen")
        }

        composable(LaundryAppScreen.StaffHome.route) {
            TODO("Staff Home Screen")
        }
    }

}