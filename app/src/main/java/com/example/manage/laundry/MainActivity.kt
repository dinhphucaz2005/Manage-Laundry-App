package com.example.manage.laundry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.manage.laundry.auth.LoginViewModel
import com.example.manage.laundry.ui.App
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            ManageLaundryAppTheme { App() }
        }
    }
}