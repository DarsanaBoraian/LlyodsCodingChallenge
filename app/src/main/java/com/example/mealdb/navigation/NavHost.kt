package com.example.mealdb.navigation

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mealdb.ui.dashboard.DashboardScreen
import com.example.mealdb.ui.mealdetails.MealDetailsScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val targetScreen = rememberSaveable { mutableStateOf(Screen.Dashboard) }

    Scaffold(topBar = { MyAppTopBar(targetScreen.value, navController) },
        content = {
            NavHost(
                navController = navController, startDestination = Screen.Dashboard.route,
            ) {
                composable(Screen.Dashboard.route) {
                    targetScreen.value = Screen.Dashboard
                    DashboardScreen(navController = navController)
                }
                composable("${Screen.MealDetails.route}/{mealId}") { backStackEntry ->
                    targetScreen.value = Screen.MealDetails
                    MealDetailsScreen(
                        backStackEntry.arguments?.getString("mealId")!!.toLong()
                    )
                }
            }
        })
}

@Composable
fun MyAppTopBar(targetScreen: Screen, navController: NavController) {
    if (targetScreen != Screen.Dashboard) {
        TopAppBar(
            title = { Text(text = targetScreen.header, color = Color.White) },
            navigationIcon = { BackButton(navController = navController) },
            backgroundColor = Color(0xFF6F7FF7)
        )
    } else {
        TopAppBar(
            title = { Text(text = targetScreen.header, color = Color.White) },
            backgroundColor = Color(0xFF6F7FF7)
        )
    }
}

@Composable
fun BackButton(navController: NavController) {
    IconButton(onClick = { navController.navigateUp() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}
