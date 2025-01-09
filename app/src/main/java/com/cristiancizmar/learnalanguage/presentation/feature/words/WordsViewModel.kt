package com.cristiancizmar.learnalanguage.presentation.feature.words

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import com.cristiancizmar.learnalanguage.domain.Word
import com.cristiancizmar.learnalanguage.utils.safeSubList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val DEFAULT_MIN_WORDS = 1
const val DEFAULT_MAX_WORDS = 5000

@HiltViewModel
class WordsViewModel @Inject constructor(private val fileWordsRepository: FileWordsRepository) :
    ViewModel() {

    enum class SORT { IDX, ATT, PERC, DIFF, ORIG }

    var state by mutableStateOf(WordsState())
        private set

    private var sort = SORT.IDX
    private var sortAsc = true
    private val allWords = getWordsWithAllMeanings()

    init {
        loadFile()
    }

    fun updateSearch(searchText: String) {
        val text = searchText.lowercase()
        if (text.isNotBlank()) {
            val searchResults = mutableListOf<Word>()
            allWords.forEach { word ->
                if (word.original.lowercase().contains(text)
                    || word.translated.lowercase().contains(text)
                ) {
                    searchResults.add(word)
                }
            }
            state = state.copy(
                searchText = text,
                searchedWords = searchResults
            )
        } else {
            state = state.copy(
                searchText = text,
                searchedWords = emptyList()
            )
        }
    }

    fun setSelectedWord(word: Word) {
        state = state.copy(
            selectedWord = word
        )
    }

    fun removeSelectedWord() {
        state = state.copy(
            selectedWord = null
        )
    }

    fun setMinWords(min: String) {
        state = state.copy(
            minWords = min.toIntOrNull()
        )
        loadFile()
    }

    fun setMaxWords(max: String) {
        state = state.copy(
            maxWords = max.toIntOrNull()
        )
        loadFile()
    }

    fun updateWordDifficulty() {
        var newDifficulty = state.selectedWord?.difficulty ?: 1
        newDifficulty++
        if (newDifficulty == 5) newDifficulty = 1

        state = state.copy(
            selectedWord = state.selectedWord?.copy(difficulty = newDifficulty)
        )
        state.selectedWord?.let { word ->
            fileWordsRepository.setWordDifficulty(word.index, newDifficulty)
        }

        loadFile()
    }

    fun updateSort(newSort: SORT) {
        if (sort == newSort) {
            sortAsc = !sortAsc
        } else {
            sort = newSort
            sortAsc = true
        }

        loadFile()
    }

    fun getFileLocale() = fileWordsRepository.getFileLocale()

    private fun getWordsWithAllMeanings(): List<Word> {
        val words = fileWordsRepository.getWordsFromFile().toMutableList()
        val finalList = words.map { it.copy() } // deep copy

        words.forEachIndexed { index, firstWord ->
            val meanings = mutableListOf<String>()
            words.forEach { secondWord ->
                if (firstWord.original == secondWord.original) {
                    meanings.add(secondWord.translated)
                }
            }
            finalList[index].translated = meanings.distinct().joinToString(", ")
        }
        return finalList
    }

    private fun loadFile() {
        val minWords = (state.minWords ?: 1) - 1
        var maxWords = (state.maxWords ?: 5000)
        if (maxWords < minWords) {
            maxWords = minWords
        }

        var newList = getWordsWithAllMeanings()
            .safeSubList(minWords, maxWords)
            .sortedBy {
                when (sort) {
                    SORT.IDX -> it.index
                    SORT.ATT -> it.attempts
                    SORT.PERC -> it.guessesPercentageInt()
                    SORT.DIFF -> it.difficulty
                    else -> it.index
                }
            }
        if (sort == SORT.ORIG) {
            newList = newList.sortedBy {
                it.original.lowercase()
            }
        }
        if (!sortAsc) {
            newList = newList.reversed()
        }
        state = state.copy(
            words = newList,
            customSorting = sort != SORT.IDX || !sortAsc || state.minWords != DEFAULT_MIN_WORDS || state.maxWords != DEFAULT_MAX_WORDS
        )
    }
}