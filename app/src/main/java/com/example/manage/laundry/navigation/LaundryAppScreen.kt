package com.example.manage.laundry.navigation

sealed class LaundryAppScreen(val route: String) {
    data object Splash : LaundryAppScreen("splash")
    data object LoginSelection : LaundryAppScreen("login_selection")
    data object CustomerLogin : LaundryAppScreen("customer_login")
    data object ShopOwnerLogin : LaundryAppScreen("shop_owner_login")
    data object StaffLogin : LaundryAppScreen("staff_login")
    data object Home : LaundryAppScreen("home")
}