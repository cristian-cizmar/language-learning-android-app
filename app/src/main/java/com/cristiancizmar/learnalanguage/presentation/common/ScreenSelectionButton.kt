package com.cristiancizmar.learnalanguage.presentation.common

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

@Composable
fun ScreenSelectionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        modifier
            .fillMaxWidth()
            .padding(40.dp, 0.dp),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(text, color = Color.White)
    }
}

@Preview
@Composable
fun ScreenSelectionButton(
) {
    ScreenSelectionButton("ScreenSelectionButton", {})
}