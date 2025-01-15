package com.cristiancizmar.learnalanguage.presentation.feature.words

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.presentation.common.BasicTextField
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.SimpleButton
import com.cristiancizmar.learnalanguage.presentation.common.WordRow
import com.cristiancizmar.learnalanguage.presentation.common.rememberTextToSpeech
import com.cristiancizmar.learnalanguage.presentation.common.speak
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme
import com.cristiancizmar.learnalanguage.presentation.theme.TransparentDarkGray

@Composable
fun WordsScreen(
    navController: NavController,
    viewModel: WordsViewModel = hiltViewModel()
) {
    val tts = rememberTextToSpeech(viewModel.getFileLocale())
    LaunchedEffect(viewModel.state.selectedWord?.original) {
        viewModel.state.selectedWord?.original?.let { text ->
            if (text.isNotBlank()) {
                speak(tts, text)
            }
        }
    }
    BackHandler {
        if (viewModel.state.selectedWord != null) {
            viewModel.onAction(WordsViewModel.WordsEvent.RemoveSelectedWord)
        } else if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }
    LearnALanguageTheme {
        Surface(color = Color.Black) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    SimpleButton(
                        text = stringResource(R.string.index_short),
                        onClick = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.UpdateSort(
                                    WordsViewModel.SORT.IDX
                                )
                            )
                        })
                    SimpleButton(
                        text = stringResource(R.string.original_short),
                        onClick = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.UpdateSort(
                                    WordsViewModel.SORT.ORIG
                                )
                            )
                        })
                    SimpleButton(
                        text = stringResource(R.string.attempts_short),
                        onClick = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.UpdateSort(
                                    WordsViewModel.SORT.ATT
                                )
                            )
                        })
                    SimpleButton(
                        text = stringResource(R.string.percentage_short),
                        onClick = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.UpdateSort(
                                    WordsViewModel.SORT.PERC
                                )
                            )
                        })
                    SimpleButton(
                        text = stringResource(R.string.difficulty_short),
                        onClick = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.UpdateSort(
                                    WordsViewModel.SORT.DIFF
                                )
                            )
                        })
                }
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    contentPadding = PaddingValues(all = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = viewModel.state.words,
                        key = { index, word -> word.index }
                    ) { index, word ->
                        WordRow(
                            word = word,
                            onClick = {
                                viewModel.onAction(
                                    WordsViewModel.WordsEvent.SetSelectedWord(
                                        word
                                    )
                                )
                            },
                            index = if (viewModel.state.customSorting) index + 1 else -1
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    contentPadding = PaddingValues(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = viewModel.state.searchedWords,
                        key = { index, word -> word.index }
                    ) { index, word ->
                        WordRow(word = word, color = Color.Cyan)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BasicTextField(
                        modifier = Modifier.requiredWidth(100.dp),
                        value = viewModel.state.minWords?.toString() ?: "",
                        onValueChange = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.SetMinWords(
                                    it.toIntOrNull()
                                )
                            )
                        },
                        label = stringResource(R.string.start_index),
                        keyboardType = KeyboardType.Number
                    )
                    BasicTextField(
                        modifier = Modifier.requiredWidth(100.dp),
                        value = viewModel.state.maxWords?.toString() ?: "",
                        onValueChange = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.SetMaxWords(
                                    it.toIntOrNull()
                                )
                            )
                        },
                        label = stringResource(R.string.end_index),
                        keyboardType = KeyboardType.Number
                    )
                }
                BasicTextField(
                    value = viewModel.state.searchText,
                    onValueChange = { viewModel.onAction(WordsViewModel.WordsEvent.UpdateSearch(it)) },
                    label = stringResource(R.string.search)
                )
            }
            if (viewModel.state.selectedWord != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { viewModel.onAction(WordsViewModel.WordsEvent.RemoveSelectedWord) }
                        .background(TransparentDarkGray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.state.selectedWord.toString(),
                        modifier = Modifier.padding(20.dp),
                        color = Color.White
                    )
                    SelectionButton(
                        text = stringResource(
                            R.string.difficulty_specify,
                            viewModel.state.selectedWord?.difficulty.toString()
                        ),
                        onClick = { viewModel.onAction(WordsViewModel.WordsEvent.UpdateWordDifficulty) }
                    )
                    BasicTextField(
                        value = viewModel.state.selectedWord?.note.toString(),
                        onValueChange = {
                            viewModel.onAction(
                                WordsViewModel.WordsEvent.SetWordNote(
                                    it
                                )
                            )
                        },
                        label = stringResource(R.string.note_word)
                    )
                }
            }
        }
    }
}