package com.example.manage.laundry.navigation

sealed class LaundryAppScreen(val route: String) {
    data object Splash : LaundryAppScreen("splash")
    data object LoginSelection : LaundryAppScreen("login_selection")
}