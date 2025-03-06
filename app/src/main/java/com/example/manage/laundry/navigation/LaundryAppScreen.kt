package com.example.manage.laundry.navigation

sealed class LaundryAppScreen(val route: String) {
    data object Splash : LaundryAppScreen("splash")
    data object LoginSelection : LaundryAppScreen("login_selection")
    data object CustomerLogin : LaundryAppScreen("customer_login")
    data object ShopOwnerLogin : LaundryAppScreen("shop_owner_login")
    data object StaffLogin : LaundryAppScreen("staff_login")
    data object ShopOwnerHome : LaundryAppScreen("shop_owner_home")
    data object StaffHome : LaundryAppScreen("staff_home")
    data object CustomerHome : LaundryAppScreen("customer_home")
}