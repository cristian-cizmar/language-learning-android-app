package com.cristiancizmar.learnalanguage.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.WideSelectionButton
import com.cristiancizmar.learnalanguage.presentation.theme.TransparentDarkGray

@Composable
fun HomeExportImport(
    hidePopup: (() -> Unit),
    onClickExport: (() -> Unit),
    onClickImport: (() -> Unit),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { hidePopup() }
            .background(TransparentDarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WideSelectionButton(
            text = stringResource(R.string.export_saved_data),
            onClick = { onClickExport() },
        )
        WideSelectionButton(
            text = stringResource(R.string.import_saved_data),
            onClick = { onClickImport() }
        )
    }
}