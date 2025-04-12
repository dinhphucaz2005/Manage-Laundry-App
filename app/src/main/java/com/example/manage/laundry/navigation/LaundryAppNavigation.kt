package com.example.manage.laundry.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.screens.LoginSelectionScreen
import com.example.manage.laundry.ui.screens.SplashScreen
import com.example.manage.laundry.ui.screens.TestScreen
import com.example.manage.laundry.viewmodel.TestViewModel

@Composable
fun LaundryAppNavigation(
    onNavigateCustomerActivity: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LaundryAppScreen.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(LaundryAppScreen.Test.route) {
            TestScreen(viewModel = fakeViewModel<TestViewModel>())
        }


        composable(LaundryAppScreen.LoginSelection.route) {
            LoginSelectionScreen(
                onShopOwnerLoginRequest = {
                    TODO()
                },
                onStaffLoginRequest = {
                    TODO()
                },
                onCustomerLoginRequest = onNavigateCustomerActivity
            )
        }

        composable(LaundryAppScreen.Splash.route) {
            SplashScreen(onNavigate = {
                navController.navigate(LaundryAppScreen.LoginSelection.route) {
                    popUpTo(LaundryAppScreen.Splash.route) { inclusive = true }
                }
            })
        }
    }
}