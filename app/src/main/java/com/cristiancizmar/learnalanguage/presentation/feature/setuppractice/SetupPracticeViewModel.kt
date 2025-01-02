package com.cristiancizmar.learnalanguage.presentation.feature.setuppractice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SetupPracticeViewModel : ViewModel() {

    var state by mutableStateOf(SetupPracticeState())
        private set

    fun updateMin(min: Int?) {
        state = state.copy(minWords = min)
    }

    fun updateMax(max: Int?) {
        state = state.copy(maxWords = max)
    }

    fun updateanswerDelay(delay: Int?) {
        state = state.copy(answerDelay = delay)
    }

    fun updateSaveResults(save: Boolean) {
        state = state.copy(saveResults = save)
    }

    fun updateDifficulty() {
        var newDifficulty = state.difficulty + 1
        if (newDifficulty == 5) {
            newDifficulty = 1
        }
        state = state.copy(difficulty = newDifficulty)
    }
}