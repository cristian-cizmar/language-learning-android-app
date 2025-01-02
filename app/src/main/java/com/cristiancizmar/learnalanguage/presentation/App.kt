package com.cristiancizmar.learnalanguage.presentation

import android.app.Application
import android.content.Context
import com.cristiancizmar.learnalanguage.data.FileWordsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        FileWordsRepository.initPreferences(this)
    }

    companion object {
        var appContext: Context? = null
    }
}