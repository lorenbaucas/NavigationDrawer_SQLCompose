package com.example.navigationdrawercompose

import com.example.navigationdrawersqlcompose.R

sealed class NavegacionItem(var route: String, var icon: Int, var title: String)
{
    object Home : NavegacionItem("home", R.drawable.ic_home, "Home")
    object Profile : NavegacionItem("profile", R.drawable.ic_profile, "Anadir")
    object Settings : NavegacionItem("settings", R.drawable.ic_settings, "Borrar")

}