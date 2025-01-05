package com.cristiancizmar.learnalanguage.presentation.feature.practice

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme
import kotlinx.coroutines.delay
import java.util.Locale

@Preview
@Composable
fun PracticeScreen(
    modifier: Modifier = Modifier,
    viewModel: PracticeViewModel = hiltViewModel(),
    minWords: Int? = null,
    maxWords: Int? = null,
    answerDelay: Int? = 500,
    saveResults: Boolean = false,
    minDifficulty: Int = 1
) {
    val tts = rememberTextToSpeech(viewModel.getFileLocale())
    LaunchedEffect(Unit) {
        delay(100L)
        speak(tts, viewModel.state.original)
    }
    LaunchedEffect(viewModel.state.original) {
        speak(tts, viewModel.state.original)
    }

    LearnALanguageTheme {
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
                    text = stringResource(R.string.lock_reminder),
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
                    SelectionButton(
                        text = if (viewModel.state.showTranslation) {
                            viewModel.state.translated
                        } else {
                            stringResource(R.string.click_for_answer)
                        },
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
                            text = stringResource(R.string.wrong),
                            onClick = { viewModel.onClickWrong() },
                            paddingHorizontal = 20,
                            paddingVertical = 10
                        )
                        SelectionButton(
                            text = stringResource(R.string.correct),
                            onClick = { viewModel.onClickCorrect() },
                            paddingHorizontal = 20,
                            paddingVertical = 10
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberTextToSpeech(locale: Locale): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = locale
            }
        }
        tts.value = textToSpeech

        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
    return tts
}

fun speak(tts: MutableState<TextToSpeech?>, text: String) {
    val modifiedText = text
        .substringAfter('.')
        .substringBefore(',')
        .substringBefore('/')
        .substringBefore('(')
    tts.value?.speak(modifiedText, TextToSpeech.QUEUE_FLUSH, null, "")
}