package com.cristiancizmar.learnalanguage.presentation.feature.words

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cristiancizmar.learnalanguage.R
import com.cristiancizmar.learnalanguage.domain.Word
import com.cristiancizmar.learnalanguage.presentation.common.BasicTextField
import com.cristiancizmar.learnalanguage.presentation.common.WordRow
import com.cristiancizmar.learnalanguage.presentation.theme.TransparentDarkGray

@Composable
fun WordsSearch(
    words: List<Word>,
    searchText: String,
    onSearchTextChange: ((String) -> Unit),
    onClose: (() -> Unit)
) {
    Column(
        modifier = Modifier
            .background(TransparentDarkGray)
            .fillMaxHeight()
            .clickable { }
            .padding(30.dp)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Image(
            painterResource(R.drawable.close),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClose() }
        )
        BasicTextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            label = stringResource(R.string.search)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = words,
                key = { index, word -> word.index }
            ) { index, word ->
                WordRow(word = word, color = Color.Cyan)
            }
        }
    }
}