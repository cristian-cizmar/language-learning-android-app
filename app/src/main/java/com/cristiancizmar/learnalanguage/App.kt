package com.cristiancizmar.learnalanguage

import android.app.Application
import android.content.Context
import com.cristiancizmar.learnalanguage.repository.FileWordsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
        FileWordsRepository.initPrefferences(this)
    }

    companion object {
        var appContext: Context? = null
    }
}