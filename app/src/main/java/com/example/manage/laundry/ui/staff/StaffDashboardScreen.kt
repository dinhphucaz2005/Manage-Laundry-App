package com.example.manage.laundry.ui.staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.StaffViewModel

@Composable
fun StaffDashboardScreen(viewModel: StaffViewModel = fakeViewModel<StaffViewModel>()) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Staff Dashboard", fontSize = 24.sp)
        Button(onClick = { /* Navigate to AwesomeAllScreen */ }) {
            Text("View Statistics")
        }
        // Add more buttons or content as needed
    }
}

@Preview
@Composable
private fun StaffDashboardScreenPreview() {
    ManageLaundryAppTheme {
        StaffDashboardScreen()
    }
}