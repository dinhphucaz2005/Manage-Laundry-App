package com.example.manage.laundry.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.customer.CustomerHomeScreen
import com.example.manage.laundry.ui.customer.CustomerLoginScreen
import com.example.manage.laundry.ui.customer.CustomerRegisterScreen
import com.example.manage.laundry.ui.owner.ShopOwnerLoginScreen
import com.example.manage.laundry.ui.owner.ShopOwnerHomeScreen
import com.example.manage.laundry.ui.owner.ShopOwnerRegisterScreen
import com.example.manage.laundry.ui.screens.LoginSelectionScreen
import com.example.manage.laundry.ui.screens.SplashScreen
import com.example.manage.laundry.ui.screens.TestScreen
import com.example.manage.laundry.ui.staff.StaffLoginScreen
import com.example.manage.laundry.viewmodel.CustomerViewModel
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel
import com.example.manage.laundry.viewmodel.StaffViewModel
import com.example.manage.laundry.viewmodel.TestViewModel

@Composable
fun LaundryAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val shopOwnerViewModel = fakeViewModel<ShopOwnerViewModel>()
    val customerViewModel = fakeViewModel<CustomerViewModel>()
    val staffViewModel = fakeViewModel<StaffViewModel>()
    NavHost(
        navController = navController,
        startDestination = LaundryAppScreen.Splash.route,
        modifier = modifier
    ) {

        composable(LaundryAppScreen.Test.route) {
            TestScreen(viewModel = fakeViewModel<TestViewModel>())
        }


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
            CustomerLoginScreen(
                viewModel = customerViewModel,
                onLoginSuccess = {
                    navController.navigate(LaundryAppScreen.CustomerHome.route) {
                        popUpTo(LaundryAppScreen.CustomerLogin.route) { inclusive = true }
                    }
                },
                onRegisterRequest = {
                    navController.navigate(LaundryAppScreen.CustomerRegister.route)
                }
            )
        }

        composable(LaundryAppScreen.CustomerRegister.route) {
            CustomerRegisterScreen(
                viewModel = customerViewModel,
                onLoginRequest = navController::popBackStack
            )
        }

        composable(LaundryAppScreen.ShopOwnerLogin.route) {
            ShopOwnerLoginScreen(
                viewModel = shopOwnerViewModel,
                onLoginSuccess = {
                    navController.navigate(LaundryAppScreen.ShopOwnerHome.route) {
                        popUpTo(LaundryAppScreen.ShopOwnerLogin.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(LaundryAppScreen.ShopOwnerRegister.route)
                }
            )
        }

        composable(LaundryAppScreen.ShopOwnerHome.route) {
            ShopOwnerHomeScreen(
                viewModel = shopOwnerViewModel,
                onLogout = {
                    navController.popBackStack()
                    shopOwnerViewModel.logout()
                }
            )
        }

        composable(LaundryAppScreen.ShopOwnerRegister.route) {
            ShopOwnerRegisterScreen(
                viewModel = shopOwnerViewModel,
                onRegisterSuccess = navController::popBackStack
            )
        }

        composable(LaundryAppScreen.StaffLogin.route) {
            StaffLoginScreen(
                viewModel = staffViewModel,
                onLoginSuccess = {
                    navController.navigate(LaundryAppScreen.StaffHome.route) {
                        popUpTo(LaundryAppScreen.StaffLogin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(LaundryAppScreen.CustomerHome.route) { CustomerHomeScreen(customerViewModel) }

        composable(LaundryAppScreen.StaffHome.route) {
            TODO("Staff Home Screen")
        }

    }

}