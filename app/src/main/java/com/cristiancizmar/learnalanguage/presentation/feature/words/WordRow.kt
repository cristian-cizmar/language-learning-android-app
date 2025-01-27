package com.cristiancizmar.learnalanguage.presentation.feature.words

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristiancizmar.learnalanguage.domain.Word

@Composable
fun WordRow(
    word: Word,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    onClick: () -> Unit = {},
    index: Int = -1
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(word.getGenderColor())
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = word.original,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.weight(1f),
                text = word.translated,
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        if (word.originalSentence.isNotBlank() && word.translatedSentence.isNotBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = word.originalSentence,
                    color = color
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = word.translatedSentence,
                    color = color
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val smallFont = 10.sp
            if (index != -1) {
                Text(text = "Current index: $index", color = color, fontSize = smallFont)
            }
            Text(text = "Index: ${word.index}", color = color, fontSize = smallFont)
            Text(text = "Attempts: ${word.attempts}", color = color, fontSize = smallFont)
            Text(
                text = "Correctness: ${word.guessesPercentage()}",
                color = color,
                fontSize = smallFont
            )
            Text(text = "Difficulty: ${word.difficulty}", color = color, fontSize = smallFont)
        }
    }
}