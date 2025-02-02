package com.cristiancizmar.learnalanguage.domain

import androidx.compose.ui.graphics.Color
import com.cristiancizmar.learnalanguage.presentation.theme.BlueDer
import com.cristiancizmar.learnalanguage.presentation.theme.GreenDas
import com.cristiancizmar.learnalanguage.presentation.theme.RedDie
import com.cristiancizmar.learnalanguage.utils.DAS_PREFIX
import com.cristiancizmar.learnalanguage.utils.DER_PREFIX
import com.cristiancizmar.learnalanguage.utils.DIE_PREFIX
import com.cristiancizmar.learnalanguage.utils.cleanWord

data class Word(
    val index: Int = 0,
    val original: String = "",
    val originalSentence: String = "",
    var translated: String = "",
    val translatedSentence: String = "",
    var correctGuesses: Int = 0,
    var attempts: Int = 0,
    var difficulty: Int = 1,
    var note: String = ""
) {
    fun guessesPercentage(): String {
        val num = if (attempts == 0) 0f
        else 100 * correctGuesses / attempts
        return "$num%"
    }

    fun guessesPercentageInt(): Int {
        return if (attempts == 0) 0
        else 100 * correctGuesses / attempts
    }

    fun getGenderColor(): Color {
        if (original.cleanWord().startsWith(DER_PREFIX)
            || translated.cleanWord().startsWith(DER_PREFIX)
        ) {
            return BlueDer
        }
        if (original.cleanWord().startsWith(DIE_PREFIX)
            || translated.cleanWord().startsWith(DIE_PREFIX)
        ) {
            return RedDie
        }
        if (original.cleanWord().startsWith(DAS_PREFIX)
            || translated.cleanWord().startsWith(DAS_PREFIX)
        ) {
            return GreenDas
        }
        return Color.Black
    }
}
