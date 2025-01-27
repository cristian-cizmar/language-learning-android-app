package com.cristiancizmar.learnalanguage.presentation.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.WideSelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.theme.TransparentDarkGray

@Composable
fun HomeLanguageSettings(
    files: List<String>,
    onSelectFile: ((String) -> Unit),
    onSetFileFavorite: ((String) -> Unit),
    hidePopup: (() -> Unit),
    selectedFileName: String,
    favoriteFileName: String,
    areLanguagesSwitched: Boolean,
    onClickSwitchLanguages: (() -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { hidePopup() }
            .background(TransparentDarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        files.forEach { file ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SelectionButton(
                    text = file,
                    onClick = { onSelectFile(file) },
                    borderColor = if (selectedFileName == file) {
                        Color.Green
                    } else {
                        Color.White
                    },
                    mainPaddingVertical = 0.dp
                )
                Image(
                    painterResource(
                        if (file == favoriteFileName) R.drawable.heart
                        else R.drawable.heart_empty
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.clickable { onSetFileFavorite(file) },
                )
            }
        }
        WideSelectionButton(
            text = stringResource(R.string.switch_languages, areLanguagesSwitched.toString()),
            onClick = { onClickSwitchLanguages() },
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}