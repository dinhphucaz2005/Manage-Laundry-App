package com.example.manage.laundry.ui.system_admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manage.laundry.data.network.ApiService
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SystemAdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageLaundryAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SystemAdminScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@HiltViewModel
class SystemAdminViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    fun sendNotification(adminKey: String, message: String) {
        viewModelScope.launch {
            try {

                val result = apiService.sendNotification(adminKey, message)
                println(result)

            } catch (e: Exception) {
                println("Error sending notification: ${e.message}")
            }
        }
    }
}


@Composable
fun SystemAdminScreen(
    modifier: Modifier = Modifier,
    systemAdminViewModel: SystemAdminViewModel = hiltViewModel<SystemAdminViewModel>()
) {

    var adminKey by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Hệ thống sẽ dừng trong 30 phút nữa để bảo trì") }
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        Color.White
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon Logo
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Customer Icon",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header
            Text(
                text = "System Admin",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Gửi thông báo đến khách hàng",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form đăng nhập
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Email
                    OutlinedTextField(
                        value = adminKey,
                        onValueChange = { adminKey = it },
                        label = { Text("Admin Key") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Email Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                        )
                    )

                    // Password
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Thông báo đến khách hàng") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                        )
                    )


                    // Nút Gửi thông báo
                    Button(
                        onClick = {
                            systemAdminViewModel.sendNotification(
                                adminKey = adminKey,
                                message = message
                            )
                            if (adminKey != "adminKey")
                                Toast.makeText(context, "Vui lòng nhập đúng Admin Key", Toast.LENGTH_SHORT).show()
                            else
                            Toast.makeText(
                                context,
                                "Đã gửi thông báo đến khách hàng thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(4.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text("Gửi thông báo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

        }
    }
}

