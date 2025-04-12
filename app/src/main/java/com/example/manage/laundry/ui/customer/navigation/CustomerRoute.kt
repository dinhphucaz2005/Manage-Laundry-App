package com.example.manage.laundry.ui.customer.navigation

object CustomerRoute {

    const val LOGIN = "login"
    const val HOME = "home"
    const val SHOP_DETAIL = "shop_detail/{shopId}"


    fun shopDetail(shopId: Int) = "shop_detail/$shopId"

}