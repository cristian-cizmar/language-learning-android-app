package com.cristiancizmar.learnalanguage.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristiancizmar.learnalanguage.domain.Word

@Composable
fun WordRow(
    word: Word,
    color: Color = Color.White,
    onClick: () -> Unit = {},
    index: Int = -1
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(word.getGenderColor())
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val smallFont = 10.sp
            if (index != -1) {
                Text(text = "($index)", color = color, fontSize = smallFont)
            }
            Text(text = "${word.index}", color = color, fontSize = smallFont)
            Text(modifier = Modifier.weight(1f), text = word.original, color = color)
            Text(modifier = Modifier.weight(1f), text = word.translated, color = color)
            Text(text = word.attempts.toString(), color = color, fontSize = smallFont)
            Text(text = word.guessesPercentage(), color = color, fontSize = smallFont)
            Text(text = "[${word.difficulty}]", color = color, fontSize = smallFont)
        }
        if (word.originalSentence.isNotBlank() && word.translatedSentence.isNotBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = word.originalSentence,
                    color = color
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = word.translatedSentence,
                    color = color
                )
            }
        }
    }
}

@Preview
@Composable
fun WordRowPreview() {
    WordRow(
        word = Word(
            0,
            "das Auto",
            "",
            "car",
            "",
            0,
            0,
            1
        )
    )
}