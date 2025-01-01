package com.cristiancizmar.learnalanguage.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ScreenSelectionButton(
) {
    ScreenSelectionButton("ScreenSelectionButton") {}
}

@Composable
fun ScreenSelectionButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        Modifier
            .fillMaxWidth()
            .padding(40.dp, 0.dp),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(text, color = Color.White)
    }
}