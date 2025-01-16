package com.cristiancizmar.learnalanguage.presentation.feature.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val fileWordsRepository: FileWordsRepository
) : ViewModel() {

    sealed class SettingsEvent {
        data class SetDelayCheckAnswer(val delay: Boolean) : SettingsEvent()
    }

    var state by mutableStateOf(SettingsState())
        private set

    init {
        state = state.copy(delayCheckAnswer = fileWordsRepository.getDelayCheckShowingAnswer())
    }

    fun onAction(practiceEvent: SettingsEvent) {
        when (practiceEvent) {
            is SettingsEvent.SetDelayCheckAnswer -> {
                setDelayCheckAnswer(practiceEvent.delay)
            }
        }
    }

    private fun setDelayCheckAnswer(delay: Boolean) {
        state = state.copy(delayCheckAnswer = delay)
        fileWordsRepository.setDelayCheckShowingAnswer(delay)
    }
}