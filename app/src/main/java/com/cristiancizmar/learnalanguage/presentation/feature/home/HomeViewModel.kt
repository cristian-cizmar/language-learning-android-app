package com.cristiancizmar.learnalanguage.presentation.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cristiancizmar.learnalanguage.data.FileWordsRepository

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(HomeState(""))
        private set

    init {
        loadText()
    }

    fun updateText(text: String) {
        state = state.copy(text = text)
        FileWordsRepository.setMainText(text)
    }

    fun onClickSwitchLanguages() {
        val newState = !state.switchLanguages
        state = state.copy(switchLanguages = newState)
        FileWordsRepository.switchLanguages = newState
    }

    private fun loadText() {
        updateText(FileWordsRepository.getMainText())
    }
}