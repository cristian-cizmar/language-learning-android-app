package com.cristiancizmar.learnalanguage.presentation.feature.home

import com.cristiancizmar.learnalanguage.data.FileWordsRepository

data class HomeState(
    val text: String,
    val switchLanguages: Boolean = FileWordsRepository.switchLanguages
)