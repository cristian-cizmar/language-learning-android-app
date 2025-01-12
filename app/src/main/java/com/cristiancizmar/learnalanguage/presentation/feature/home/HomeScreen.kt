package com.cristiancizmar.learnalanguage.presentation.feature.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.ScreenSelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
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
                    placeholder = { Text(text = stringResource(R.string.note_placeholder)) }
                )
                ScreenSelectionButton(
                    text = stringResource(R.string.words_list),
                    onClick = { navController.navigate(route = Screen.Words.route) },
                    modifier = Modifier.padding(top = 5.dp)
                )
                ScreenSelectionButton(
                    text = stringResource(R.string.practice),
                    onClick = { navController.navigate(route = Screen.SetupPractice.route) }
                )

                ScreenSelectionButton(
                    text = stringResource(
                        R.string.switch_languages,
                        viewModel.state.switchLanguages.toString()
                    ),
                    onClick = { viewModel.onAction(HomeViewModel.HomeEvent.SwitchLanguages) },
                    modifier = Modifier.padding(top = 15.dp)
                )
                viewModel.state.files.forEach { file ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SelectionButton(
                            text = file,
                            onClick = {
                                viewModel.onAction(
                                    HomeViewModel.HomeEvent.UpdateSelectedFileName(file)
                                )
                            },
                            borderColor =
                            if (viewModel.state.selectedFileName == file) Color.Green
                            else Color.White,
                            mainPaddingVertical = 0.dp
                        )
                        Image(
                            painterResource(
                                if (file == viewModel.state.favoriteFileName) R.drawable.heart
                                else R.drawable.heart_empty
                            ),
                            contentDescription = "",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.clickable {
                                viewModel.onAction(
                                    HomeViewModel.HomeEvent.UpdateFavoriteFileName(file)
                                )
                            },
                        )
                    }
                }

                ScreenSelectionButton(
                    text = stringResource(R.string.export_saved_data),
                    onClick = { shareBackupFile(ctx) },
                    modifier = Modifier.padding(top = 15.dp)
                )
                ScreenSelectionButton(
                    text = stringResource(R.string.import_saved_data),
                    onClick = { filePickerLauncher.launch(arrayOf("text/*")) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearnALanguageTheme {
        HomeScreen(navController = rememberNavController(), viewModel = viewModel())
    }
}