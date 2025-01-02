package com.cristiancizmar.learnalanguage.domain

import androidx.compose.ui.graphics.Color
import com.cristiancizmar.learnalanguage.presentation.theme.BlueDer
import com.cristiancizmar.learnalanguage.presentation.theme.GreenDas
import com.cristiancizmar.learnalanguage.presentation.theme.RedDie
import com.cristiancizmar.learnalanguage.utils.cleanWord

data class Word(
    val index: Int = 0,
    val original: String = "",
    val originalSentence: String = "",
    var translated: String = "",
    val translatedSentence: String = "",
    var correctGuesses: Int = 0,
    var attempts: Int = 0,
    var difficulty: Int = 1
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
        val derPrefix = "der "
        val diePrefix = "die "
        val dasPrefix = "das "
        if (original.cleanWord().startsWith(derPrefix)
            || translated.cleanWord().startsWith(derPrefix)
        ) {
            return BlueDer
        }
        if (original.cleanWord().startsWith(diePrefix)
            || translated.cleanWord().startsWith(diePrefix)
        ) {
            return RedDie
        }
        if (original.cleanWord().startsWith(dasPrefix)
            || translated.cleanWord().startsWith(dasPrefix)
        ) {
            return GreenDas
        }
        return Color.Black
    }
}
