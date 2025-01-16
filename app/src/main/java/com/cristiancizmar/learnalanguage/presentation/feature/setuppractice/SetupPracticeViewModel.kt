package com.cristiancizmar.learnalanguage.presentation.feature.setuppractice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val DELAY_SHOW_ANSWER_AMOUNT = 500

@HiltViewModel
class SetupPracticeViewModel @Inject constructor(fileWordsRepository: FileWordsRepository) :
    ViewModel() {

    sealed class SetupPracticeEvent {
        data class UpdateMinIndex(val index: Int?) : SetupPracticeEvent()
        data class UpdateMaxIndex(val index: Int?) : SetupPracticeEvent()
        data object UpdateSaveResults : SetupPracticeEvent()
        data object UpdateDifficulty : SetupPracticeEvent()
    }

    var state by mutableStateOf(SetupPracticeState())
        private set

    init {
        if (fileWordsRepository.getDelayCheckShowingAnswer()) {
            setAnswerDelay()
        }
    }

    fun onAction(event: SetupPracticeEvent) {
        when (event) {
            is SetupPracticeEvent.UpdateMinIndex -> {
                updateMin(event.index)
            }

            is SetupPracticeEvent.UpdateMaxIndex -> {
                updateMax(event.index)
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

    private fun setAnswerDelay() {
        state = state.copy(answerDelay = DELAY_SHOW_ANSWER_AMOUNT)
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