package com.cristiancizmar.learnalanguage.presentation.feature.practice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme

@Preview
@Composable
fun PracticeScreen(
    modifier: Modifier = Modifier,
    viewModel: PracticeViewModel = viewModel(),
    minWords: Int? = null,
    maxWords: Int? = null,
    answerDelay: Int? = 500,
    saveResults: Boolean = false,
    minDifficulty: Int = 1
) {
    val context = LocalContext.current
    LearnALanguageTheme() {
        Surface(color = Color.Black) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(10.dp, 10.dp)
                        .fillMaxWidth(),
                    text = viewModel.state.details,
                    color = Color.White,
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .padding(10.dp, 10.dp)
                        .fillMaxWidth(),
                    text = "remember to lock your app",
                    color = Color.White,
                    textAlign = TextAlign.End
                )
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .fillMaxWidth(),
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
                    SelectionButton(
                        text = if (viewModel.state.showTranslation) viewModel.state.translated else "click for answer",
                        onClick = { viewModel.onClickShowAnswer() },
                        paddingHorizontal = 20,
                        paddingVertical = 10
                    )
                }
                if (viewModel.state.showCheckButtons) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SelectionButton(
                            text = "Wrong",
                            { viewModel.onClickWrong() },
                            paddingHorizontal = 20,
                            paddingVertical = 10
                        )
                        SelectionButton(
                            text = "Correct",
                            { viewModel.onClickCorrect() },
                            paddingHorizontal = 20,
                            paddingVertical = 10
                        )
                    }
                }
            }
        }
    }
}