package com.example.dimmerlamp.ui.navigation

import com.example.dimmerlamp.R

sealed class NavItem(val icon: Int, val iconEnable:Int, val route:String, val title:Int) {
//    object Splash: NavItem(icon = 0, iconEnable = 0, route = "splash", title = 0)
    object Home: NavItem(icon = 0, iconEnable = 0, route = "home", title =
        R.string.home)
    object Topic: NavItem(icon = 0, iconEnable = 0, route = "topic", title =
        R.string.topic
    )
}