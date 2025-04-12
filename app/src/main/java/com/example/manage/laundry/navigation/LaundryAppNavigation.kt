package com.example.manage.laundry.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.ui.splash.LoginSelectionScreen
import com.example.manage.laundry.ui.splash.SplashScreen

@Composable
fun LaundryAppNavigation(
    onNavigateCustomerActivity: () -> Unit,
    onNavigateShopOwnerActivity: () -> Unit,
    onNavigateStaffActivity: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LaundryAppScreen.Splash.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(LaundryAppScreen.LoginSelection.route) {
            LoginSelectionScreen(
                onShopOwnerLoginRequest = onNavigateShopOwnerActivity,
                onStaffLoginRequest = onNavigateStaffActivity,
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