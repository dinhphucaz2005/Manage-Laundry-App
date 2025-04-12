package com.example.manage.laundry.navigation

sealed class LaundryAppScreen(val route: String) {
    data object Test : LaundryAppScreen("test")
    data object Splash : LaundryAppScreen("splash")
    data object LoginSelection : LaundryAppScreen("login_selection")

//    data object CustomerLogin : LaundryAppScreen("customer_login")
//    data object CustomerHome : LaundryAppScreen("customer_home")
//    data object CustomerRegister : LaundryAppScreen("customer_register")
//    data object CustomerOrder : LaundryAppScreen("customer_order")
//
//    data object ShopOwnerLogin : LaundryAppScreen("shop_owner_login")
//    data object ShopOwnerHome : LaundryAppScreen("shop_owner_home")
//    data object ShopOwnerRegister : LaundryAppScreen("shop_owner_register")
//
//    data object StaffLogin : LaundryAppScreen("staff_login")
//    data object StaffHome : LaundryAppScreen("staff_home")
//    data object StaffRegister : LaundryAppScreen("staff_register")
}