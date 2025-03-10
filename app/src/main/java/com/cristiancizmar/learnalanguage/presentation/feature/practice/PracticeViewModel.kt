package com.cristiancizmar.learnalanguage.presentation.feature.practice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import com.cristiancizmar.learnalanguage.domain.Word
import com.cristiancizmar.learnalanguage.utils.safeSubList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MIN_WORDS = "minWords"
const val MAX_WORDS = "maxWords"
const val ANSWER_DELAY = "answerDelay"
const val SAVE_RESULTS = "saveResults"
const val DIFFICULTY = "difficulty"

@HiltViewModel
class PracticeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fileWordsRepository: FileWordsRepository
) : ViewModel() {

    sealed class PracticeEvent {
        data object ShowAnswer : PracticeEvent()
        data object ClickCorrect : PracticeEvent()
        data object ClickWrong : PracticeEvent()
        data object ShowQuitConfirmationDialog : PracticeEvent()
        data object CancelQuitDialog : PracticeEvent()
    }

    private var minWords = 0
    private var maxWords = 0
    private var answerDelay = 500
    private var saveResults = false
    private var minDifficulty = 1
    private var currentWord = Word()
    private var allWords: List<Word> = emptyList()
    private var remainingWords: MutableList<Word> = mutableListOf()

    var state by mutableStateOf(PracticeState())
        private set

    init {
        savedStateHandle.get<Int>(MIN_WORDS)?.let {
            minWords = it - 1
        }
        savedStateHandle.get<Int>(MAX_WORDS)?.let {
            maxWords = it
        }
        savedStateHandle.get<Int>(ANSWER_DELAY)?.let {
            answerDelay = it
        }
        savedStateHandle.get<Boolean>(SAVE_RESULTS)?.let {
            saveResults = it
        }
        savedStateHandle.get<Int>(DIFFICULTY)?.let {
            minDifficulty = it
        }
        state = state.copy(switchLanguages = fileWordsRepository.switchLanguages)
        loadWordsAndInit()
    }

    fun onAction(practiceEvent: PracticeEvent) {
        when (practiceEvent) {
            PracticeEvent.ShowAnswer -> {
                onClickShowAnswer()
            }

            PracticeEvent.ClickCorrect -> {
                onClickCorrect()
            }

            PracticeEvent.ClickWrong -> {
                onClickWrong()
            }

            PracticeEvent.ShowQuitConfirmationDialog -> {
                showQuitConfirmationDialog()
            }

            PracticeEvent.CancelQuitDialog -> {
                hideQuitConfirmationDialog()
            }
        }
    }

    fun getFileLocale() = fileWordsRepository.getFileLocale()

    private fun onClickShowAnswer() {
        state = state.copy(
            showTranslation = true,
            wordNote = currentWord.note
        )

        viewModelScope.launch {
            delay(answerDelay.toLong())
            state = state.copy(
                showCheckButtons = true
            )
        }
    }

    private fun onClickCorrect() {
        if (saveResults) {
            fileWordsRepository.setWordCorrectness(currentWord.index, true)
        }
        state = state.copy(
            correct = state.correct + 1
        )
        loadNextWord()
    }

    private fun onClickWrong() {
        if (saveResults) {
            fileWordsRepository.setWordCorrectness(currentWord.index, false)
        }
        state = state.copy(
            wrong = state.wrong + 1
        )
        loadNextWord()
    }

    private fun loadWordsAndInit() {
        allWords = fileWordsRepository.getWordsFromFile()
        remainingWords = allWords
            .safeSubList(minWords, maxWords)
            .filter { it.difficulty >= minDifficulty }
            .toMutableList()
        loadNextWord()
    }

    private fun loadNextWord() {
        if (remainingWords.isNotEmpty()) {
            currentWord = remainingWords.random()
            remainingWords.remove(currentWord)
            state = state.copy(
                original = "${currentWord.original} (${currentWord.index})",
                translated = getAllMeaningsOfWord(currentWord),
                showTranslation = false,
                showCheckButtons = false,
                details = getDetails(),
                wordNote = ""
            )
        } else {
            state = state.copy(
                original = "no words left",
                showTranslation = false,
                showCheckButtons = false,
                details = getDetails(),
                ended = true,
                wordNote = ""
            )
        }
    }

    private fun getAllMeaningsOfWord(currentWord: Word): String {
        val translations = mutableListOf<String>()
        allWords.forEach { word ->
            if (word.original == currentWord.original) {
                translations.add(word.translated)
            }
        }
        return translations.joinToString(", ")
    }

    private fun getDetails() =
        "Save: $saveResults, index: ${minWords + 1}-$maxWords\nDifficulty: ${minDifficulty}, remaining: ${remainingWords.size}"

    private fun showQuitConfirmationDialog() {
        state = state.copy(
            showConfirmDialog = true
        )
    }

    private fun hideQuitConfirmationDialog() {
        state = state.copy(
            showConfirmDialog = false
        )
    }

}