package com.cristiancizmar.learnalanguage.presentation.feature.home

data class HomeState(
    val text: String,
    val switchLanguages: Boolean = false,
    val files: List<String>
)