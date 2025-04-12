package com.example.manage.laundry.ui.staff

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.manage.laundry.BaseActivity
import com.example.manage.laundry.LocalSnackbarHostState
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
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = StaffRoute.LOGIN,
                            modifier = Modifier.padding(paddingValues)
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
    }
}
