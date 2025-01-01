package com.cristiancizmar.learnalanguage.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cristiancizmar.learnalanguage.ui.components.SelectionButton
import com.cristiancizmar.learnalanguage.ui.components.WhiteTextField
import com.cristiancizmar.learnalanguage.ui.theme.LearnALanguageTheme

@Preview
@Composable
fun SetupPracticeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SetupPracticeViewModel = viewModel()
) {
    val context = LocalContext.current
    LearnALanguageTheme() {
        Surface(color = Color.Black) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WhiteTextField(
                    value = viewModel.state.minWords?.toString() ?: "",
                    onValueChange = { viewModel.updateMin(it.toIntOrNull()) },
                    label = "min words"
                )
                WhiteTextField(
                    value = viewModel.state.maxWords?.toString() ?: "",
                    onValueChange = { viewModel.updateMax(it.toIntOrNull()) },
                    label = "max words"
                )
                WhiteTextField(
                    value = viewModel.state.answerDelay?.toString() ?: "",
                    onValueChange = { viewModel.updateanswerDelay(it.toIntOrNull()) },
                    label = "translation delay"
                )
                SelectionButton(
                    text = "Save results: ${viewModel.state.saveResults}",
                    onClick = { viewModel.updateSaveResults(!viewModel.state.saveResults) }
                )
                SelectionButton(
                    text = "Min difficulty: ${if (viewModel.state.difficulty == 1) "all" else viewModel.state.difficulty}",
                    onClick = { viewModel.updateDifficulty() }
                )
                SelectionButton(
                    text = "Continue",
                    onClick = {
                        navController.navigate(
                            route = "Practice/${viewModel.state.minWords ?: 1}&${viewModel.state.maxWords ?: 5000}&${viewModel.state.answerDelay ?: 500}&${viewModel.state.saveResults}&${viewModel.state.difficulty}"
                        )
                    }
                )
            }
        }
    }
}

data class SetupPracticeState(
    val minWords: Int? = null,
    val maxWords: Int? = null,
    val answerDelay: Int? = 500,
    val saveResults: Boolean = false,
    val difficulty: Int = 1
)