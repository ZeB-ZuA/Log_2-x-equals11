package com.udistrital.log_2xequals11

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udistrital.log_2xequals11.Logic.Board
import com.udistrital.log_2xequals11.Views.GameScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    val board = Board()
    NavHost(navController = navController, startDestination = AppViews.splashScreen.route){
        composable(route= AppViews.gameScreen.route){
            GameScreen(navController = navController)
        }
        composable(route= AppViews.splashScreen.route){
            SplashScreen(navController = navController)
        }
    }
    
    
    
    
    
}