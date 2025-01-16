package com.cristiancizmar.learnalanguage.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cristiancizmar.learnalanguage.presentation.feature.home.HomeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.practice.PracticeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.settings.SettingsScreen
import com.cristiancizmar.learnalanguage.presentation.feature.setuppractice.SetupPracticeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.words.WordsScreen

sealed class Screen(val route: String) {
    data object Home : Screen(route = "Home")
    data object Words : Screen(route = "Words")
    data object SetupPractice : Screen(route = "SetupPractice")
    data object Settings : Screen(route = "Settings")
    data object Practice :
        Screen(route = "Practice/{minWords}&{maxWords}&{answerDelay}&{saveResults}&{difficulty}")
}

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navHostController)
        }
        composable(
            route = Screen.Words.route
        ) {
            WordsScreen(navController = navHostController)
        }
        composable(
            route = Screen.SetupPractice.route
        ) {
            SetupPracticeScreen(navController = navHostController)
        }
        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen()
        }
        composable(
            route = Screen.Practice.route,
            arguments = listOf(
                navArgument("minWords") { type = NavType.IntType },
                navArgument("maxWords") { type = NavType.IntType },
                navArgument("answerDelay") { type = NavType.IntType },
                navArgument("saveResults") { type = NavType.BoolType },
                navArgument("difficulty") { type = NavType.IntType },
            )
        ) {
            PracticeScreen(
                navController = navHostController
            )
        }
    }
}