package com.cristiancizmar.learnalanguage.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cristiancizmar.learnalanguage.presentation.feature.home.HomeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.practice.PracticeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.setuppractice.SetupPracticeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.words.WordsScreen

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
            WordsScreen()
        }
        composable(
            route = Screen.SetupPractice.route
        ) {
            SetupPracticeScreen(navController = navHostController)
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
        ) { backStackEntry ->
            PracticeScreen(
                minWords = backStackEntry.arguments!!.getInt("minWords"),
                maxWords = backStackEntry.arguments!!.getInt("maxWords"),
                answerDelay = backStackEntry.arguments!!.getInt("answerDelay"),
                saveResults = backStackEntry.arguments!!.getBoolean("saveResults"),
                minDifficulty = backStackEntry.arguments!!.getInt("difficulty")
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen(route = "Home")
    object Words : Screen(route = "Words")
    object SetupPractice : Screen(route = "SetupPractice")
    object Practice :
        Screen(route = "Practice/{minWords}&{maxWords}&{answerDelay}&{saveResults}&{difficulty}")
}