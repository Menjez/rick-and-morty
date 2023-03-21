package com.example.ktorapi.navigation

sealed class Routes(val route:String){
    object Character: Routes ("Character")
    object Details: Routes("Details")
}