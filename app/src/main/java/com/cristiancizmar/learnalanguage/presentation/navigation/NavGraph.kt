package com.cristiancizmar.learnalanguage.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cristiancizmar.learnalanguage.presentation.feature.home.HomeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.practice.ANSWER_DELAY
import com.cristiancizmar.learnalanguage.presentation.feature.practice.DIFFICULTY
import com.cristiancizmar.learnalanguage.presentation.feature.practice.MAX_WORDS
import com.cristiancizmar.learnalanguage.presentation.feature.practice.MIN_WORDS
import com.cristiancizmar.learnalanguage.presentation.feature.practice.PracticeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.practice.SAVE_RESULTS
import com.cristiancizmar.learnalanguage.presentation.feature.settings.SettingsScreen
import com.cristiancizmar.learnalanguage.presentation.feature.setuppractice.SetupPracticeScreen
import com.cristiancizmar.learnalanguage.presentation.feature.words.WordsScreen

sealed class Screen(val route: String) {
    data object Home : Screen(route = "Home")
    data object Words : Screen(route = "Words")
    data object SetupPractice : Screen(route = "SetupPractice")
    data object Settings : Screen(route = "Settings")
    data object Practice :
        Screen(route = "Practice/{$MIN_WORDS}&{$MAX_WORDS}&{$ANSWER_DELAY}&{$SAVE_RESULTS}&{$DIFFICULTY}")
}

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screen.Words.route) {
            WordsScreen(navController = navHostController)
        }
        composable(route = Screen.SetupPractice.route) {
            SetupPracticeScreen(navController = navHostController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navHostController)
        }
        composable(
            route = Screen.Practice.route,
            arguments = listOf(
                navArgument(MIN_WORDS) { type = NavType.IntType },
                navArgument(MAX_WORDS) { type = NavType.IntType },
                navArgument(ANSWER_DELAY) { type = NavType.IntType },
                navArgument(SAVE_RESULTS) { type = NavType.BoolType },
                navArgument(DIFFICULTY) { type = NavType.IntType },
            )
        ) {
            PracticeScreen(navController = navHostController)
        }
    }
}