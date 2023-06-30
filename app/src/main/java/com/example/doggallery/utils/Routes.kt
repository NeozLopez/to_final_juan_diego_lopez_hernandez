package com.example.doggallery.utils

sealed class Routes(var route:String){
    object ScreenMain:Routes("screen_main")
    object ScreenFavs:Routes("screen_favs")
}