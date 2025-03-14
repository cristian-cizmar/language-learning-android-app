package com.cristiancizmar.learnalanguage.presentation.common

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

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