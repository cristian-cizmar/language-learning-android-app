package com.cristiancizmar.learnalanguage.presentation.feature.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import com.cristiancizmar.learnalanguage.presentation.common.ScreenSelectionButton
import com.cristiancizmar.learnalanguage.presentation.navigation.Screen
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val files = FileWordsRepository.getFileNames()
    val ctx = LocalContext.current

    var importFileName by remember { mutableStateOf("No file selected") }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            importFileName = uri?.lastPathSegment ?: "No file selected"
            if (uri != null) {
                onFileSelected(ctx, uri)
            }
        }
    )

    LearnALanguageTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = viewModel.state.text,
                    onValueChange = { viewModel.updateText(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Black,
                        cursorColor = Color.White,
                        disabledLabelColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(Modifier.padding(5.dp))
                ScreenSelectionButton(
                    text = "Words list",
                    onClick = { navController.navigate(route = Screen.Words.route) }
                )
                ScreenSelectionButton(
                    text = "Practice",
                    onClick = { navController.navigate(route = Screen.SetupPractice.route) }
                )

                Spacer(Modifier.padding(15.dp))
                ScreenSelectionButton(
                    text = "Switch languages = ${viewModel.state.switchLanguages}",
                    onClick = {
                        viewModel.onClickSwitchLanguages()
                    }
                )
                files.forEach { file ->
                    ScreenSelectionButton(
                        text = file,
                        onClick = {
                            FileWordsRepository.fileName = file
                        }
                    )
                }

                Spacer(Modifier.padding(15.dp))
                ScreenSelectionButton(
                    text = "Export saved data",
                    onClick = { onClickExport(ctx) }
                )
                ScreenSelectionButton(
                    text = "Import saved data",
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

private fun onFileSelected(context: Context, uri: Uri) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val content = reader.use { it.readText() }
    inputStream?.close()
    FileWordsRepository.importBackup(content)
}

private fun onClickExport(context: Context) {
    val backupFileName = "learn-a-language-backup.txt"
    context.openFileOutput(backupFileName, Context.MODE_PRIVATE)
        .use {
            it.write(FileWordsRepository.getAllData().toByteArray())
        }
    val file = File(context.filesDir, backupFileName)

    if (file.exists()) {
        val fileUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    }
}