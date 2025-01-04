package com.cristiancizmar.learnalanguage.presentation.feature.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fileWordsRepository: FileWordsRepository) :
    ViewModel() {

    var state by mutableStateOf(HomeState(text = "", files = fileWordsRepository.getFileNames()))
        private set

    init {
        loadText()
        state = state.copy(switchLanguages = fileWordsRepository.switchLanguages)
    }

    fun updateText(text: String) {
        state = state.copy(text = text)
        fileWordsRepository.setMainText(text)
    }

    fun onClickSwitchLanguages() {
        val newState = !state.switchLanguages
        state = state.copy(switchLanguages = newState)
        fileWordsRepository.switchLanguages = newState
    }

    fun updateSelectedFileName(fileName: String) {
        fileWordsRepository.fileName = fileName
    }

    fun importBackupFromFile(context: Context, uri: Uri) {
        fileWordsRepository.importBackupFromFile(context, uri)
    }

    private fun loadText() {
        updateText(fileWordsRepository.getMainText())
    }
}