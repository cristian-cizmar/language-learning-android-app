package com.cristiancizmar.learnalanguage.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingHorizontal: Int = 0,
    paddingVertical: Int = 0,
) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        Modifier
            .padding(10.dp, 10.dp),
        border = BorderStroke(2.dp, Color.White),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
    ) {
        Text(
            modifier = modifier.padding(paddingHorizontal.dp, paddingVertical.dp),
            text = text,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun SelectionButton(
) {
    SelectionButton(
        "SelectionButton",
        {},
        paddingHorizontal = 20,
        paddingVertical = 20,
    )
}