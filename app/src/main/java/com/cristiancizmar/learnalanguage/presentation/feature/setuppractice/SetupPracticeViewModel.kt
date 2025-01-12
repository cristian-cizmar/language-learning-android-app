package com.cristiancizmar.learnalanguage.presentation.feature.setuppractice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupPracticeViewModel @Inject constructor() : ViewModel() {

    sealed class SetupPracticeEvent {
        data class UpdateMinIndex(val index: Int?) : SetupPracticeEvent()
        data class UpdateMaxIndex(val index: Int?) : SetupPracticeEvent()
        data class UpdateAnswerDelay(val delay: Int?) : SetupPracticeEvent()
        data object UpdateSaveResults : SetupPracticeEvent()
        data object UpdateDifficulty : SetupPracticeEvent()
    }

    var state by mutableStateOf(SetupPracticeState())
        private set

    fun onAction(event: SetupPracticeEvent) {
        when (event) {
            is SetupPracticeEvent.UpdateMinIndex -> {
                updateMin(event.index)
            }
            is SetupPracticeEvent.UpdateMaxIndex -> {
                updateMax(event.index)
            }
            is SetupPracticeEvent.UpdateAnswerDelay -> {
                updateAnswerDelay(event.delay)
            }
            SetupPracticeEvent.UpdateSaveResults -> {
                updateSaveResults(!state.saveResults)
            }
            SetupPracticeEvent.UpdateDifficulty -> {
                updateDifficulty()
            }
        }
    }

    private fun updateMin(min: Int?) {
        state = state.copy(minWords = min)
    }

    private fun updateMax(max: Int?) {
        state = state.copy(maxWords = max)
    }

    private fun updateAnswerDelay(delay: Int?) {
        state = state.copy(answerDelay = delay)
    }

    private fun updateSaveResults(save: Boolean) {
        state = state.copy(saveResults = save)
    }

    private fun updateDifficulty() {
        var newDifficulty = state.difficulty + 1
        if (newDifficulty == 5) {
            newDifficulty = 1
        }
        state = state.copy(difficulty = newDifficulty)
    }
}