package com.cristiancizmar.learnalanguage.presentation.feature.words

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristiancizmar.learnalanguage.presentation.common.SelectionButton
import com.cristiancizmar.learnalanguage.presentation.common.SimpleButton
import com.cristiancizmar.learnalanguage.presentation.common.BasicTextField
import com.cristiancizmar.learnalanguage.presentation.common.WordRow
import com.cristiancizmar.learnalanguage.presentation.theme.LearnALanguageTheme

@Preview
@Composable
fun WordsScreen(
    modifier: Modifier = Modifier,
    viewModel: WordsViewModel = viewModel()
) {
    LearnALanguageTheme {
        Surface(color = Color.Black) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BasicTextField(
                        modifier = Modifier.requiredWidth(100.dp),
                        value = viewModel.state.minWords?.toString() ?: "",
                        onValueChange = { viewModel.setMinWords(it) },
                        label = "MinWords"
                    )
                    BasicTextField(
                        modifier = Modifier.requiredWidth(100.dp),
                        value = viewModel.state.maxWords?.toString() ?: "",
                        onValueChange = { viewModel.setMaxWords(it) },
                        label = "MaxWords"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    SimpleButton(
                        text = "IDX",
                        onClick = { viewModel.updateSort(WordsViewModel.SORT.IDX) })
                    SimpleButton(
                        text = "ORIG",
                        onClick = { viewModel.updateSort(WordsViewModel.SORT.ORIG) })
                    SimpleButton(
                        text = "ATT",
                        onClick = { viewModel.updateSort(WordsViewModel.SORT.ATT) })
                    SimpleButton(
                        text = "PERC",
                        onClick = { viewModel.updateSort(WordsViewModel.SORT.PERC) })
                    SimpleButton(
                        text = "DIFF",
                        onClick = { viewModel.updateSort(WordsViewModel.SORT.DIFF) })
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
                            onClick = { viewModel.setSelectedWord(word) },
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
                BasicTextField(
                    value = viewModel.state.searchText,
                    onValueChange = { viewModel.updateSearch(it) },
                    label = "Search"
                )
            }
            if (viewModel.state.selectedWord != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            viewModel.removeSelectedWord()
                        }
                        .background(Color(0, 0, 0, 200)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SelectionButton(
                        text = viewModel.state.selectedWord.toString(),
                        onClick = { }
                    )
                    SelectionButton(
                        text = "Difficulty: ${viewModel.state.selectedWord?.difficulty}",
                        onClick = { viewModel.updateWordDifficulty() }
                    )
                }
            }
        }
    }
}