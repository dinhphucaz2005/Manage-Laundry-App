package com.example.manage.laundry.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.ui.customer.CustomerLoginScreen
import com.example.manage.laundry.ui.owner.ShopOwnerLoginScreen
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
        composable(LaundryAppScreen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(LaundryAppScreen.LoginSelection.route) {
            LoginSelectionScreen(navController = navController)
        }

        composable(LaundryAppScreen.CustomerLogin.route) {
            CustomerLoginScreen(viewModel = hiltViewModel<CustomerViewModel>())
        }

        composable(LaundryAppScreen.ShopOwnerLogin.route) {
            ShopOwnerLoginScreen(viewModel = hiltViewModel<ShopOwnerViewModel>())
        }

        composable(LaundryAppScreen.StaffLogin.route) {
            StaffLoginScreen(viewModel = hiltViewModel<StaffViewModel>())
        }

        composable(LaundryAppScreen.Home.route) {
            // HomeScreen ở đây
        }
    }
}