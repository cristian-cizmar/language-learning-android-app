package com.cristiancizmar.learnalanguage.presentation.feature.words

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import com.cristiancizmar.learnalanguage.domain.Word
import com.cristiancizmar.learnalanguage.utils.cleanWord
import com.cristiancizmar.learnalanguage.utils.safeSubList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val DEFAULT_MIN_WORDS = 1
const val DEFAULT_MAX_WORDS = 5000

@HiltViewModel
class WordsViewModel @Inject constructor(private val fileWordsRepository: FileWordsRepository) :
    ViewModel() {

    sealed class WordsEvent {
        data class SetMinWords(val words: Int?) : WordsEvent()
        data class SetMaxWords(val words: Int?) : WordsEvent()
        data class UpdateSort(val sort: SORT) : WordsEvent()
        data class SetSelectedWord(val word: Word) : WordsEvent()
        data class UpdateSearch(val search: String) : WordsEvent()
        data class SetWordNote(val note: String) : WordsEvent()
        data object RemoveSelectedWord : WordsEvent()
        data object UpdateWordDifficulty : WordsEvent()
        data object ShowSearchPopup : WordsEvent()
        data object HideSearchPopup : WordsEvent()
    }

    enum class SORT { IDX, ATT, PERC, DIFF, ORIG }

    var state by mutableStateOf(WordsState())
        private set

    private var sort = SORT.IDX
    private var sortAsc = true
    private lateinit var allWords: List<Word>

    init {
        loadFile()
        viewModelScope.launch {
            allWords = getWordsWithAllMeanings()
        }
    }

    fun onAction(event: WordsEvent) {
        when (event) {
            is WordsEvent.SetMinWords -> {
                setMinWords(event.words)
            }

            is WordsEvent.SetMaxWords -> {
                setMaxWords(event.words)
            }

            is WordsEvent.UpdateSort -> {
                updateSort(event.sort)
            }

            is WordsEvent.SetSelectedWord -> {
                setSelectedWord(event.word)
            }

            is WordsEvent.UpdateSearch -> {
                updateSearch(event.search)
            }

            is WordsEvent.SetWordNote -> {
                setWordNote(event.note)
            }

            WordsEvent.RemoveSelectedWord -> {
                removeSelectedWord()
            }

            WordsEvent.UpdateWordDifficulty -> {
                updateWordDifficulty()
            }

            WordsEvent.ShowSearchPopup -> {
                showSearchPopup()
            }

            WordsEvent.HideSearchPopup -> {
                hideSearchPopup()
            }
        }
    }

    fun getFileLocale() = fileWordsRepository.getFileLocale()

    private fun updateSearch(searchText: String) {
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

    private fun setSelectedWord(word: Word) {
        state = state.copy(
            selectedWord = word
        )
    }

    private fun removeSelectedWord() {
        state = state.copy(
            selectedWord = null
        )
    }

    private fun setMinWords(min: Int?) {
        state = state.copy(
            minWords = min
        )
        loadFile()
    }

    private fun setMaxWords(max: Int?) {
        state = state.copy(
            maxWords = max
        )
        loadFile()
    }

    private fun updateWordDifficulty() {
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

    private fun setWordNote(note: String) {
        state = state.copy(
            selectedWord = state.selectedWord?.copy(note = note)
        )
        state.selectedWord?.let { word ->
            fileWordsRepository.setWordNote(word.index, note)
        }
        loadFile()
    }

    private fun updateSort(newSort: SORT) {
        if (sort == newSort) {
            sortAsc = !sortAsc
        } else {
            sort = newSort
            sortAsc = true
        }

        loadFile()
    }

    private suspend fun getWordsWithAllMeanings() = withContext(Dispatchers.IO) {
        val words = fileWordsRepository.getWordsFromFile().toMutableList()
        val finalList = words.map { it.copy() } // deep copy

        words.forEachIndexed { index, word ->
            val meanings = mutableListOf<String>()
            words.forEach { secondWord ->
                if (word.original == secondWord.original) {
                    meanings.add(secondWord.translated)
                }
            }
            finalList[index].translated = meanings.distinct().joinToString(", ")
        }
        finalList
    }

    private fun loadFile() {
        viewModelScope.launch {
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
                    it.original.cleanWord()
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

    private fun showSearchPopup() {
        state = state.copy(
            showSearchPopup = true
        )
    }

    private fun hideSearchPopup() {
        state = state.copy(
            showSearchPopup = false
        )
    }
}