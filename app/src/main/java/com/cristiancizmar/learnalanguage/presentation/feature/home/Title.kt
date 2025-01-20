package com.cristiancizmar.learnalanguage.presentation.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

private const val GITHUB_LINK = "https://github.com/cristian-cizmar/language-learning-android-app"

@Composable
fun Title() {
    val uriHandler = LocalUriHandler.current
    ConstraintLayout {
        val (title, subtitle) = createRefs()
        Text(
            text = "Language Learning",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )
        Text(
            text = "app by Cristian Cizmar",
            color = Color.White,
            modifier = Modifier
                .clickable { uriHandler.openUri(GITHUB_LINK) }
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom)
                    end.linkTo(title.end)
                }
        )
    }
}