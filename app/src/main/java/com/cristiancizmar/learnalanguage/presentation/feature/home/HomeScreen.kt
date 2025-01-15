package com.cristiancizmar.learnalanguage.presentation.feature.home

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.WideSelectionButton
import com.cristiancizmar.learnalanguage.presentation.navigation.Screen
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme
import com.cristiancizmar.learnalanguage.utils.shareBackupFile

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val ctx = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                viewModel.onAction(HomeViewModel.HomeEvent.ImportBackupFromFile(ctx, uri))
            }
        }
    )
    BackHandler {
        if (viewModel.state.showDataPopup) {
            viewModel.onAction(HomeViewModel.HomeEvent.HideDataPopup)
        } else if (viewModel.state.showLanguagePopup) {
            viewModel.onAction(HomeViewModel.HomeEvent.HideLanguagePopup)
        } else {
            navController.popBackStack()
        }
    }
    LearnALanguageTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WideSelectionButton(
                    text = stringResource(R.string.words_list),
                    onClick = { navController.navigate(route = Screen.Words.route) },
                    modifier = Modifier.padding(top = 45.dp)
                )
                WideSelectionButton(
                    text = stringResource(R.string.practice),
                    onClick = { navController.navigate(route = Screen.SetupPractice.route) }
                )
                WideSelectionButton(
                    text = "Language",
                    onClick = { viewModel.onAction(HomeViewModel.HomeEvent.ShowLanguagePopup) },
                    modifier = Modifier.padding(top = 15.dp)
                )
                WideSelectionButton(
                    text = "Import / export progress",
                    onClick = { viewModel.onAction(HomeViewModel.HomeEvent.ShowDataPopup) }
                )
                TextField(
                    value = viewModel.state.notesText,
                    onValueChange = { viewModel.onAction(HomeViewModel.HomeEvent.UpdateNotesText(it)) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Black,
                        cursorColor = Color.White,
                        disabledLabelColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = stringResource(R.string.note_placeholder)) },
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            if (viewModel.state.showLanguagePopup) {
                HomeLanguageSettings(
                    files = viewModel.state.files,
                    onSelectFile = { file: String ->
                        viewModel.onAction(HomeViewModel.HomeEvent.UpdateSelectedFileName(file))
                    },
                    onSetFileFavorite = { file: String ->
                        viewModel.onAction(HomeViewModel.HomeEvent.UpdateFavoriteFileName(file))
                    },
                    hidePopup = { viewModel.onAction(HomeViewModel.HomeEvent.HideLanguagePopup) },
                    selectedFileName = viewModel.state.selectedFileName,
                    favoriteFileName = viewModel.state.favoriteFileName,
                    areLanguagesSwitched = viewModel.state.switchLanguages,
                    onClickSwitchLanguages = { viewModel.onAction(HomeViewModel.HomeEvent.SwitchLanguages) }
                )
            }
            if (viewModel.state.showDataPopup) {
                HomeExportImport(
                    hidePopup = { viewModel.onAction(HomeViewModel.HomeEvent.HideDataPopup) },
                    onClickExport = { shareBackupFile(ctx) },
                    onClickImport = { filePickerLauncher.launch(arrayOf("text/*")) }
                )
            }
        }
    }
}