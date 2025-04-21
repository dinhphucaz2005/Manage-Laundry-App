package com.example.manage.laundry.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.LocalSnackbarHostState
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme

@Composable
fun LoginSelectionScreen(
    onShopOwnerLoginRequest: () -> Unit,
    onStaffLoginRequest: () -> Unit,
    onCustomerLoginRequest: () -> Unit,
    onSystemAdminLoginRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Your Role",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        val snackbarHostState = LocalSnackbarHostState.current
        val coroutineScope = rememberCoroutineScope()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                RoleSelectionCard(
                    title = "Customer",
                    icon = Icons.Default.Person,
                    onClick = onCustomerLoginRequest,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
            item {
                RoleSelectionCard(
                    title = "Shop Owner",
                    icon = Icons.Default.Store,
                    onClick = onShopOwnerLoginRequest,
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
            item {
                RoleSelectionCard(
                    title = "Staff",
                    icon = Icons.Default.Work,
                    onClick = onStaffLoginRequest,
                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
            item {
                RoleSelectionCard(
                    title = "System Admin",
                    icon = Icons.Default.Settings,
                    onClick = onSystemAdminLoginRequest,
                    backgroundColor = MaterialTheme.colorScheme.error
                )
            }

        }
    }
}

@Composable
fun RoleSelectionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .size(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

        }
    }
}

@Preview
@Composable
private fun LoginSelectionScreenPreview() {
    ManageLaundryAppTheme {
        LoginSelectionScreen(
            onShopOwnerLoginRequest = { TODO() },
            onStaffLoginRequest = { TODO() },
            onCustomerLoginRequest = { TODO() },
            onSystemAdminLoginRequest = { TODO() }
        )
    }
}