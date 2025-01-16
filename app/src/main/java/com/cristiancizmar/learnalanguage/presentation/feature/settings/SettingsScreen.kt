package com.cristiancizmar.learnalanguage.presentation.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    LearnALanguageTheme {
        Surface(color = Color.Black) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Add a small delay before checking your answer during practice",
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = viewModel.state.delayCheckAnswer,
                        onCheckedChange = { delay ->
                            viewModel.onAction(
                                SettingsViewModel.SettingsEvent.SetDelayCheckAnswer(delay)
                            )
                        },
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
    }
}