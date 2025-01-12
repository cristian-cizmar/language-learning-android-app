package com.cristiancizmar.learnalanguage.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingHorizontal: Int = 0,
    paddingVertical: Int = 0,
    borderColor: Color = Color.White,
    mainPaddingHorizontal: Dp = 10.dp,
    mainPaddingVertical: Dp = 10.dp,

    ) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        Modifier
            .padding(mainPaddingHorizontal, mainPaddingVertical),
        border = BorderStroke(2.dp, borderColor),
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