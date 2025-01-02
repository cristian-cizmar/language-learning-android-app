package com.cristiancizmar.learnalanguage.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.cristiancizmar.learnalanguage.presentation.navigation.SetupNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeInitial()
        }
    }
}

@Composable
fun HomeInitial() {
    val navController = rememberNavController()
    SetupNavGraph(navHostController = navController)
}