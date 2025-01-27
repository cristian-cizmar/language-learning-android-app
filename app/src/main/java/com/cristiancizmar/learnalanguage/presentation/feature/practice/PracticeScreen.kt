package com.cristiancizmar.learnalanguage.presentation.feature.practice

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.SimpleButton
import com.cristiancizmar.learnalanguage.presentation.common.TopAppBar
import com.cristiancizmar.learnalanguage.presentation.common.WideSelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.rememberTextToSpeech
import com.cristiancizmar.learnalanguage.presentation.common.speak
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme
import com.cristiancizmar.learnalanguage.utils.percentage
import kotlinx.coroutines.delay

@Composable
fun PracticeScreen(
    navController: NavController,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    val tts = rememberTextToSpeech(viewModel.getFileLocale())
    LaunchedEffect(Unit) {
        delay(100L)
        if (!viewModel.state.switchLanguages) {
            speak(tts, viewModel.state.original)
        }
    }
    LaunchedEffect(viewModel.state.original) {
        if (!viewModel.state.switchLanguages) {
            speak(tts, viewModel.state.original)
        }
    }
    LaunchedEffect(viewModel.state.showCheckButtons) {
        if (viewModel.state.showCheckButtons && viewModel.state.switchLanguages) {
            speak(tts, viewModel.state.translated)
        }
    }
    BackHandler {
        viewModel.onAction(PracticeViewModel.PracticeEvent.ShowQuitConfirmationDialog)
    }
    LearnALanguageTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    onClickBack = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    },
                    content = {
                        Text(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(10.dp, 10.dp)
                                .fillMaxWidth(),
                            text = viewModel.state.details,
                            color = Color.Gray,
                            textAlign = TextAlign.End
                        )
                    })
            },
            content = { padding ->
                Surface(
                    color = Color.Black,
                    modifier = Modifier.padding(padding)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (viewModel.state.correct != 0 || viewModel.state.wrong != 0) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 10.dp, end = 10.dp)
                                    .fillMaxWidth(),
                                text = percentage(viewModel.state.correct, viewModel.state.wrong),
                                color = Color.White,
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )
                            Text(
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .fillMaxWidth(),
                                text = "${viewModel.state.correct} / ${viewModel.state.correct + viewModel.state.wrong}",
                                color = Color.White,
                                textAlign = TextAlign.End,
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .fillMaxWidth(),
                            text = viewModel.state.wordNote,
                            color = Color.White,
                            textAlign = TextAlign.End
                        )
                        Column(
                            modifier = Modifier
                                .weight(4f)
                                .fillMaxWidth()
                                .clickable { speak(tts, viewModel.state.original) },
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxWidth(),
                                text = viewModel.state.original,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                        if (!viewModel.state.ended) {
                            WideSelectionButton(
                                text = if (viewModel.state.showTranslation) {
                                    viewModel.state.translated
                                } else {
                                    stringResource(R.string.answer)
                                },
                                onClick = { viewModel.onAction(PracticeViewModel.PracticeEvent.ShowAnswer) },
                                modifier = Modifier.padding(vertical = 10.dp),
                                innerModifier = Modifier.padding(vertical = 10.dp)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SelectionButton(
                                    text = stringResource(R.string.wrong),
                                    onClick = { viewModel.onAction(PracticeViewModel.PracticeEvent.ClickWrong) },
                                    paddingHorizontal = 20,
                                    paddingVertical = 10,
                                    enabled = viewModel.state.showCheckButtons
                                )
                                SelectionButton(
                                    text = stringResource(R.string.correct),
                                    onClick = { viewModel.onAction(PracticeViewModel.PracticeEvent.ClickCorrect) },
                                    paddingHorizontal = 20,
                                    paddingVertical = 10,
                                    enabled = viewModel.state.showCheckButtons
                                )
                            }
                        }
                    }
                    if (viewModel.state.showConfirmDialog) {
                        AlertDialog(
                            backgroundColor = Color.White,
                            onDismissRequest = {
                                viewModel.onAction(PracticeViewModel.PracticeEvent.CancelQuitDialog)
                            },
                            title = {
                                Text(
                                    text = "Are you sure you want to quit?",
                                    color = Color.Black
                                )
                            },
                            confirmButton = {
                                SimpleButton(
                                    text = "Yes",
                                    textColor = Color.Black,
                                    modifier = Modifier.padding(20.dp),
                                    onClick = {
                                        if (navController.previousBackStackEntry != null) {
                                            navController.popBackStack()
                                        }
                                    }
                                )
                            },
                            dismissButton = {
                                SimpleButton(
                                    text = "No",
                                    textColor = Color.Black,
                                    modifier = Modifier.padding(20.dp),
                                    onClick = {
                                        viewModel.onAction(PracticeViewModel.PracticeEvent.CancelQuitDialog)
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}