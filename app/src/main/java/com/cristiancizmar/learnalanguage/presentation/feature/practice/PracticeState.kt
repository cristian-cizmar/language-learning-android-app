package com.cristiancizmar.learnalanguage.presentation.feature.practice

data class PracticeState(
    val original: String = "",
    val translated: String = "",
    val showTranslation: Boolean = false,
    val showCheckButtons: Boolean = false,
    val details: String = "",
    val ended: Boolean = false
)