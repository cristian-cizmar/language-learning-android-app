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

    sealed class HomeEvent {
        data class ImportBackupFromFile(val context: Context, val uri: Uri) : HomeEvent()
        data class UpdateNotesText(val note: String) : HomeEvent()
        data class UpdateSelectedFileName(val fileName: String) : HomeEvent()
        data class UpdateFavoriteFileName(val fileName: String) : HomeEvent()
        data object SwitchLanguages : HomeEvent()
    }

    var state by mutableStateOf(
        HomeState(
            notesText = "",
            files = fileWordsRepository.getFileNames()
        )
    )
        private set

    init {
        loadNotesText()
        state = state.copy(
            switchLanguages = fileWordsRepository.switchLanguages,
            selectedFileName = fileWordsRepository.fileName.orEmpty(),
            favoriteFileName = getFavoriteFileName()
        )
    }

    fun onAction(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.ImportBackupFromFile -> {
                importBackupFromFile(homeEvent.context, homeEvent.uri)
            }

            is HomeEvent.UpdateNotesText -> {
                updateNotesText(homeEvent.note)
            }

            is HomeEvent.UpdateSelectedFileName -> {
                updateSelectedFileName(homeEvent.fileName)
            }

            is HomeEvent.UpdateFavoriteFileName -> {
                updateFavoriteFileName(homeEvent.fileName)
            }

            HomeEvent.SwitchLanguages -> {
                switchLanguages()
            }

        }
    }

    private fun switchLanguages() {
        val newState = !state.switchLanguages
        state = state.copy(switchLanguages = newState)
        fileWordsRepository.switchLanguages = newState
    }

    private fun updateSelectedFileName(fileName: String) {
        fileWordsRepository.fileName = fileName
        state = state.copy(
            selectedFileName = fileName
        )
    }

    private fun updateFavoriteFileName(fileName: String) {
        fileWordsRepository.setFavoriteFileName(fileName)
        state = state.copy(
            favoriteFileName = fileName
        )
    }

    private fun getFavoriteFileName() = fileWordsRepository.getFavoriteFileName()

    private fun importBackupFromFile(context: Context, uri: Uri) {
        fileWordsRepository.importBackupFromFile(context, uri)
        loadNotesText()
    }

    private fun loadNotesText() {
        updateNotesText(fileWordsRepository.getMainText())
    }

    private fun updateNotesText(text: String) {
        state = state.copy(notesText = text)
        fileWordsRepository.setMainText(text)
    }
}