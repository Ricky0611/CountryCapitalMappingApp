package com.example.countrycomposeapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    MainNavHost(navController)
}

@Composable
fun MainNavHost(navController: NavHostController) {
    
    NavHost(navController = navController, startDestination = "countryScreen") {
        composable("countryScreen") {
            CountryScreen(
                onCountryClick = {
                    navController.navigate("capitalScreen/${it}")
                }
            )
        }
        
        composable(
            "capitalScreen/{capital}",
            arguments = listOf(navArgument("capital") { type = NavType.StringType})
        ) {
            CapitalScreen(capital = it.arguments?.getString("capital") ?: "")
        }
    }
}