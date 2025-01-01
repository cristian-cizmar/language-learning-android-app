package com.cristiancizmar.learnalanguage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cristiancizmar.learnalanguage.repository.FileWordsRepository
import com.cristiancizmar.learnalanguage.ui.components.ScreenSelectionButton
import com.cristiancizmar.learnalanguage.ui.theme.LearnALanguageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeInitial()
        }
    }
}

@Composable
fun HomeInitial() {
    val navController = rememberNavController()
    SetupNavGraph(navHostController = navController)
}

@Composable
fun Home(navController: NavController, viewModel: MainViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val files = FileWordsRepository.getFileNames()

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
                Spacer(Modifier.padding(5.dp))
                ScreenSelectionButton(
                    text = "Practice",
                    onClick = { navController.navigate(route = Screen.SetupPractice.route) }
                )

                Spacer(Modifier.padding(25.dp))
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
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearnALanguageTheme {
        Home(navController = rememberNavController(), viewModel = viewModel())
    }
}

data class MainState(
    val text: String,
    val switchLanguages: Boolean = FileWordsRepository.switchLanguages
)