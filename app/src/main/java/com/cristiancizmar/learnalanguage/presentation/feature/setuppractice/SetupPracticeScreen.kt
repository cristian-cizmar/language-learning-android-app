package com.cristiancizmar.learnalanguage.presentation.feature.setuppractice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.BasicTextField
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.TopAppBar
import com.cristiancizmar.learnalanguage.presentation.common.WideSelectionButton
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme

@Composable
fun SetupPracticeScreen(
    navController: NavController,
    viewModel: SetupPracticeViewModel = hiltViewModel()
) {
    LearnALanguageTheme {
        Scaffold(
            topBar = {
                TopAppBar {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .padding(bottom = 100.dp)
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BasicTextField(
                        value = viewModel.state.minWords?.toString() ?: "",
                        onValueChange = {
                            viewModel.onAction(
                                SetupPracticeViewModel.SetupPracticeEvent.UpdateMinIndex(
                                    it.toIntOrNull()
                                )
                            )
                        },
                        label = stringResource(R.string.start_index),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    BasicTextField(
                        value = viewModel.state.maxWords?.toString() ?: "",
                        onValueChange = {
                            viewModel.onAction(
                                SetupPracticeViewModel.SetupPracticeEvent.UpdateMaxIndex(
                                    it.toIntOrNull()
                                )
                            )
                        },
                        label = stringResource(R.string.end_index),
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SelectionButton(
                        text = stringResource(
                            R.string.save_results,
                            viewModel.state.saveResults.toString()
                        ),
                        onClick = { viewModel.onAction(SetupPracticeViewModel.SetupPracticeEvent.UpdateSaveResults) }
                    )
                    SelectionButton(
                        text = stringResource(
                            R.string.min_difficulty,
                            viewModel.state.difficulty.toString()
                        ),
                        onClick = { viewModel.onAction(SetupPracticeViewModel.SetupPracticeEvent.UpdateDifficulty) }
                    )
                    WideSelectionButton(
                        text = stringResource(R.string.continue_text),
                        onClick = {
                            navController.navigate(
                                route = "Practice/${viewModel.state.minWords ?: 1}&${viewModel.state.maxWords ?: 5000}&${viewModel.state.answerDelay}&${viewModel.state.saveResults}&${viewModel.state.difficulty}"
                            )
                        },
                        modifier = Modifier.padding(vertical = 20.dp),
                        innerModifier = Modifier.padding(vertical = 10.dp)
                    )
                }

            }
        )
    }
}