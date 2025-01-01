package com.cristiancizmar.learnalanguage.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristiancizmar.learnalanguage.model.Word
import com.cristiancizmar.learnalanguage.repository.FileWordsRepository
import com.cristiancizmar.learnalanguage.utils.safeSubList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PracticeViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private var minWords = 0
    private var maxWords = 0
    private var answerDelay = 500
    private var saveResults = false
    private var minDifficulty = 1
    private var correct = 0
    private var wrong = 0
    private var currentWord = Word()
    private var allWords: List<Word> = emptyList()
    private var remainingWords: MutableList<Word> = mutableListOf()

    var state by mutableStateOf(PracticeState())
        private set

    init {
        savedStateHandle.get<Int>("minWords")?.let {
            minWords = it - 1
        }
        savedStateHandle.get<Int>("maxWords")?.let {
            maxWords = it
        }
        savedStateHandle.get<Int>("answerDelay")?.let {
            answerDelay = it
        }
        savedStateHandle.get<Boolean>("saveResults")?.let {
            saveResults = it
        }
        savedStateHandle.get<Int>("difficulty")?.let {
            minDifficulty = it
        }

        loadWordsAndInit()
    }

    fun onClickShowAnswer() {
        state = state.copy(
            showTranslation = true
        )

        viewModelScope.launch {
            delay(answerDelay.toLong())
            state = state.copy(
                showCheckButtons = true
            )
        }
    }

    fun onClickCorrect() {
        if (saveResults) {
            FileWordsRepository.setWordCorrectness(currentWord.index, true)
        }
        correct++
        loadNextWord()
    }

    fun onClickWrong() {
        if (saveResults) {
            FileWordsRepository.setWordCorrectness(currentWord.index, false)
        }
        wrong++
        loadNextWord()
    }

    private fun loadWordsAndInit() {
        allWords = FileWordsRepository.getWordsFromFile()
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
                original = "${currentWord.index}. ${currentWord.original}",
                translated = getAllMeaningsOfWord(currentWord),
                showTranslation = false,
                showCheckButtons = false,
                details = getDetails()
            )
        } else {
            state = state.copy(
                original = "no words left",
                showTranslation = false,
                showCheckButtons = false,
                details = getDetails(),
                ended = true
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

    private fun percentage(): String {
        return if (correct + wrong == 0) "?%"
        else "${(100 * correct / (correct + wrong))}%"
    }

    private fun getDetails() =
        "delay=$answerDelay save=$saveResults, min=$minWords, max=$maxWords, remaining=${remainingWords.size}, $correct/${correct + wrong}, ${percentage()}, diff=${currentWord.difficulty}"

}