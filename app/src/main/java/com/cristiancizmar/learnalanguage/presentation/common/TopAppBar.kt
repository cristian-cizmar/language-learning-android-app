package com.cristiancizmar.learnalanguage.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme

@Composable
fun TopAppBar(
    onClickBack: (() -> Unit),
    content: @Composable () -> Unit = {},
) {
    LearnALanguageTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
        ) {
            Image(
                painterResource(R.drawable.back),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .clickable { onClickBack() }
                    .padding(20.dp)
                    .wrapContentSize()
            )
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
        }
    }
}