package com.example.manage.laundry.ui.staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.StaffViewModel

@Composable
fun StaffProfileScreen(viewModel: StaffViewModel = fakeViewModel<StaffViewModel>()) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Staff Profile", fontSize = 24.sp)
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { /* Save profile changes */ }) {
            Text("Save")
        }
    }
}

@Preview
@Composable
private fun StaffProfileScreenPreview() {
    ManageLaundryAppTheme {
        StaffProfileScreen()
    }
}