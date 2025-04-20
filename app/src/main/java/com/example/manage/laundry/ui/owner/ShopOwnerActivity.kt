package com.example.manage.laundry.ui.owner

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.BaseActivity
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.owner.navigation.ShopOwnerRoute
import com.example.manage.laundry.ui.owner.screen.ShopOwnerHomeScreen
import com.example.manage.laundry.ui.owner.screen.ShopOwnerLoginScreen
import com.example.manage.laundry.ui.staff.navigation.StaffRoute
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel

class ShopOwnerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageLaundryAppTheme {
                val navController = rememberNavController()
                val shopOwnerViewModel = fakeViewModel<ShopOwnerViewModel>()

                NavHost(
                    navController = navController,
                    startDestination = StaffRoute.LOGIN,
                    modifier = Modifier.fillMaxSize()
                ) {

                    composable(route = ShopOwnerRoute.LOGIN) {
                        ShopOwnerLoginScreen(
                            shopOwnerViewModel = shopOwnerViewModel,
                            onLoginSuccess = {
                                navController.navigate(ShopOwnerRoute.HOME) {
                                    popUpTo(StaffRoute.LOGIN) {
                                        inclusive = true
                                    }
                                }
                            },
                        ) {
                            TODO()
                        }
                    }

                    composable(route = StaffRoute.HOME) {
                        ShopOwnerHomeScreen(
                            viewModel = shopOwnerViewModel,
                            onLogout = {
                                navController.navigate(StaffRoute.LOGIN) {
                                    popUpTo(ShopOwnerRoute.HOME) {
                                        inclusive = true
                                    }
                                }
                            },
                        )
                    }

                }
            }
        }
    }
}