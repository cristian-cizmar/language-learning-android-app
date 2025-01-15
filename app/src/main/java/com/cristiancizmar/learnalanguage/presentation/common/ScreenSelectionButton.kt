package com.cristiancizmar.learnalanguage.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WideSelectionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    innerModifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        modifier
            .fillMaxWidth()
            .padding(40.dp, 0.dp),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = innerModifier
        )
    }
}

@Preview
@Composable
fun WideSelectionButton(
) {
    WideSelectionButton("ScreenSelectionButton", {})
}