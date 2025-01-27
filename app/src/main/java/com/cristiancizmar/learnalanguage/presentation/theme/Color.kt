package com.cristiancizmar.learnalanguage.presentation.theme

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val GreenLight = Color(0xFF7FF274)
val Green = Color(0xFF40CC33)
val GreenDark = Color(0xFF198F0E)
val Secondary = Color(0xFF248754)

val BlueDer = Color(0xFF000287)
val RedDie = Color(0xFF870044)
val GreenDas = Color(0xFF148700)

val TransparentDarkGray = Color(0xE4000000)

@Composable
fun transparentTextFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = Color.White,
    backgroundColor = Color.Black,
    cursorColor = Color.White,
    disabledLabelColor = Color.White,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent
)