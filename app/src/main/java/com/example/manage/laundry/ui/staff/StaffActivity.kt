package com.example.manage.laundry.ui.staff

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
import com.example.manage.laundry.ui.staff.navigation.StaffRoute
import com.example.manage.laundry.ui.staff.screen.StaffScreen
import com.example.manage.laundry.ui.staff.screen.auth.StaffLoginScreen
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme

class StaffActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageLaundryAppTheme {
                val navController = rememberNavController()
                val staffViewModel = fakeViewModel<StaffViewModel>()

                NavHost(
                    navController = navController,
                    startDestination = StaffRoute.LOGIN,
                    modifier = Modifier.fillMaxSize()
                ) {

                    composable(route = StaffRoute.LOGIN) {
                        StaffLoginScreen(
                            viewModel = staffViewModel,
                            onLoginSuccess = {
                                navController.navigate(StaffRoute.HOME) {
                                    popUpTo(StaffRoute.LOGIN) {
                                        inclusive = true
                                    }
                                }
                            },
                        )
                    }

                    composable(route = StaffRoute.HOME) {
                        StaffScreen(staffViewModel = staffViewModel)
                    }

                }
            }
        }
    }
}
