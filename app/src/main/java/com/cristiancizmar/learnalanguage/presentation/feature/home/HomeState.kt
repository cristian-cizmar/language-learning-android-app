package com.cristiancizmar.learnalanguage.presentation.feature.home

data class HomeState(
    val notesText: String,
    val switchLanguages: Boolean = false,
    val files: List<String>,
    val selectedFileName: String = "",
    val favoriteFileName: String = "",
    val showLanguagePopup: Boolean = false,
    val showDataPopup: Boolean = false
)