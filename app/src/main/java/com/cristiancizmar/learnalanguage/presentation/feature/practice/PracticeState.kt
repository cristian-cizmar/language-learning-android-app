package com.cristiancizmar.learnalanguage.presentation.feature.practice

data class PracticeState(
    val original: String = "",
    val translated: String = "",
    val showTranslation: Boolean = false,
    val showCheckButtons: Boolean = false,
    val details: String = "",
    val ended: Boolean = false,
    val wordNote: String = "",
    val showConfirmDialog: Boolean = false,
    val correct: Int = 0,
    val wrong: Int = 0
)