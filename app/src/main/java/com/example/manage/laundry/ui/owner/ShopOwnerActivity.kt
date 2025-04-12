package com.example.manage.laundry.ui.owner

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.manage.laundry.BaseActivity
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.owner.screen.ShopOwnerHomeScreen
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.ShopOwnerViewModel

class ShopOwnerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageLaundryAppTheme {
                ShopOwnerHomeScreen(
                    viewModel = fakeViewModel<ShopOwnerViewModel>(),
                    onLogout = ::finish,
                )
            }
        }
    }
}