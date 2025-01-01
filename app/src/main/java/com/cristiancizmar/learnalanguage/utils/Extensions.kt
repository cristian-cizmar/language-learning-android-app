package com.cristiancizmar.learnalanguage.utils

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase

// https://stackoverflow.com/questions/59553126/java-or-kotlin-create-as-much-of-sublist-as-possible
// if your list has 3 elements but you do list.subList(0,5) you get IndexOutOfBoundsException
// even though you would've wanted just the list with the 3 elements
// this Extension fixes the issue and it also fixes the similar scenario with the 'fromIndex'.
// Additionally, I added one more ".coerceAtMost(this.size)" to fix the crash that appeared when
// fromIndex was greater than the size of the list
fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int): List<T> =
    this.subList(
        fromIndex.coerceAtLeast(0).coerceAtMost(this.size),
        toIndex.coerceAtMost(this.size)
    )

fun String.cleanWord() = toLowerCase(Locale.current).replace("\"", "")