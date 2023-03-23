package com.example.ktorapi.ui.navigation

sealed class Routes(val route:String){
    object Characters: Routes("Character")
    object Character: Routes("Details")
}