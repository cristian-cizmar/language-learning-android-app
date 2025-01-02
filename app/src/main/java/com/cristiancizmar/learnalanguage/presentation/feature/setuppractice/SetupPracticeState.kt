package com.cristiancizmar.learnalanguage.presentation.feature.setuppractice

data class SetupPracticeState(
    val minWords: Int? = null,
    val maxWords: Int? = null,
    val answerDelay: Int? = 500,
    val saveResults: Boolean = false,
    val difficulty: Int = 1
)