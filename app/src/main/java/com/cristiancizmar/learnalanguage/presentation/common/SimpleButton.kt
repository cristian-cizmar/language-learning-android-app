package com.cristiancizmar.learnalanguage.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SimpleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    Text(
        modifier = modifier.clickable { onClick() },
        text = text,
        color = textColor
    )
}