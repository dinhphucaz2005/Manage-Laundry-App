package com.example.manage.laundry

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.manage.laundry.navigation.LaundryAppNavigation
import com.example.manage.laundry.ui.customer.CustomerActivity
import com.example.manage.laundry.ui.owner.ShopOwnerActivity
import com.example.manage.laundry.ui.staff.StaffActivity
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageLaundryAppTheme {
                LaundryAppNavigation(
                    onNavigateCustomerActivity = {
                        val intent = Intent(this@MainActivity, CustomerActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    onNavigateShopOwnerActivity = {
                        val intent = Intent(this@MainActivity, ShopOwnerActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    onNavigateStaffActivity = {
                        val intent = Intent(this@MainActivity, StaffActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}