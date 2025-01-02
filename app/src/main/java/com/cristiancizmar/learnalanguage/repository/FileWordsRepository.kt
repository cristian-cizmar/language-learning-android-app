package com.cristiancizmar.learnalanguage.repository

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.cristiancizmar.learnalanguage.App
import com.cristiancizmar.learnalanguage.model.Word

object FileWordsRepository {

    var fileName = "spanish.txt" // todo update
    var switchLanguages = false
    private var prefs: SharedPreferences? = null
    private const val packageName = "com.cristiancizmar.learnalanguage"

    fun initPrefferences(context: Context) {
        prefs = context.getSharedPreferences(
            packageName,
            Context.MODE_PRIVATE
        )
    }

    fun getWordsFromFile(): List<Word> {
        val content = try {
            val context = App.appContext ?: return emptyList()
            val fileContent = context.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }
            "\n$fileContent\n"
        } catch (e: java.lang.Exception) {
            ""
        }
        val words = content.split("\n")
        val wordsList = words
            .map { it.split("\\s+".toRegex()).take(5).map { field -> field.replace("_", " ") } }
            .map {
                val w = if (it.size == 5) {
                    Word(
                        index = it.getOrNull(0)?.toIntOrNull() ?: 0,
                        original = it.getOrNull(1) ?: "",
                        translated = it.getOrNull(2) ?: "",
                        originalSentence = it.getOrNull(3) ?: "",
                        translatedSentence = it.getOrNull(4) ?: ""
                    )
                } else {
                    Word(
                        index = it.getOrNull(0)?.toIntOrNull() ?: 0,
                        original = it.getOrNull(1) ?: "",
                        translated = it.getOrNull(2) ?: ""
                    )
                }
                if (switchLanguages) {
                    w.copy(original = w.translated, translated = w.original)
                } else {
                    w
                }
            }
        wordsList.forEach { word ->
            word.correctGuesses = getWordCorrectness(word.index, true)
            word.attempts = word.correctGuesses + getWordCorrectness(word.index, false)
            word.difficulty = getWordDifficulty(word.index)
        }
        return wordsList.subList(1, wordsList.size - 1)
    }

    fun getFileNames(): List<String> {
        val context = App.appContext ?: return emptyList()
        val items = arrayListOf<String>()
        val assetManager: AssetManager = context.assets
        for (file in assetManager.list("")!!) {
            if (file.endsWith(".txt")) items.add(file)
        }
        items.sort()
        return items
    }

    fun setMainText(text: String) {
        val editor = prefs!!.edit()
        editor.putString("$packageName.mainText", text)
        editor.apply()
    }

    fun getMainText() = prefs!!.getString("$packageName.mainText", "") ?: ""

    fun setWordCorrectness(wordId: Int, correct: Boolean) {
        val prefId = "$packageName.$fileName.$wordId.$correct"
        val prevValue = prefs!!.getInt(prefId, 0)
        val editor = prefs!!.edit()
        editor.putInt(prefId, prevValue + 1)
        editor.apply()
    }

    fun getWordCorrectness(wordId: Int, correct: Boolean): Int {
        val prefId = "$packageName.$fileName.$wordId.$correct"
        return prefs!!.getInt(prefId, 0)
    }


    fun setWordDifficulty(wordId: Int, difficulty: Int) {
        val prefId = "$packageName.$fileName.$wordId.difficulty"
        val editor = prefs!!.edit()
        editor.putInt(prefId, difficulty)
        editor.apply()
    }

    fun getWordDifficulty(wordId: Int): Int {
        val prefId = "$packageName.$fileName.$wordId.difficulty"
        return prefs!!.getInt(prefId, 1)
    }

    fun getAllData() = prefs?.all.toString()

    fun importBackup(fileContent: String) {
        prefs?.edit()?.clear()?.apply()

        val savedValuesList = fileContent
            .substring(1, fileContent.length - 1)
            .split(", $packageName.")
        val cleanValuesList = mutableListOf<String>()

        savedValuesList.forEach {
            if (it.contains("$packageName.")) {
                // for the first item in the list
                cleanValuesList.add(it.replace("$packageName.", ""))
            } else {
                cleanValuesList.add(it)
            }
        }

        cleanValuesList.forEach {
            val prefKey = it.substringBefore("=")
            val prefValue = it.substringAfter("=")

            if (prefValue.isDigitsOnly()) {
                prefValue.toIntOrNull()?.let { intPrefValue ->
                    saveIntValue(prefKey, intPrefValue)
                }
            } else {
                setMainText(prefValue)
            }
        }
    }

    private fun saveIntValue(prefKey: String, prefIntValue: Int) {
        Log.d("FileWordsRepository", "save $prefKey - $prefIntValue")
        val prefId = "$packageName.$prefKey"
        val editor = prefs!!.edit()
        editor.putInt(prefId, prefIntValue)
        editor.apply()
    }
}