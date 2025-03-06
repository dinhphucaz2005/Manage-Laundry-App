package com.example.manage.laundry.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.manage.laundry.navigation.LaundryAppScreen

@Composable
fun LoginSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Your Role",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { navController.navigate(LaundryAppScreen.CustomerLogin.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Customer Login", fontSize = 16.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { navController.navigate(LaundryAppScreen.ShopOwnerLogin.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Shop Owner Login", fontSize = 16.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { navController.navigate(LaundryAppScreen.StaffLogin.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Staff Login", fontSize = 16.sp)
        }
    }
}