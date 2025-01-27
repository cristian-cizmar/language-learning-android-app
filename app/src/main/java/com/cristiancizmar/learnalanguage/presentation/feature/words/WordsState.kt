package com.cristiancizmar.learnalanguage.presentation.feature.words

import com.cristiancizmar.learnalanguage.domain.Word
import com.cristiancizmar.learnalanguage.presentation.feature.words.WordsViewModel.SORT

data class WordsState(
    val words: List<Word> = emptyList(),
    val searchedWords: List<Word> = emptyList(),
    val searchText: String = "",
    val selectedWord: Word? = null,
    val customSorting: Boolean = false,
    val minWords: Int? = DEFAULT_MIN_WORDS,
    val maxWords: Int? = DEFAULT_MAX_WORDS,
    val showSearchPopup: Boolean = false,
    val sort: SORT = SORT.IDX
)