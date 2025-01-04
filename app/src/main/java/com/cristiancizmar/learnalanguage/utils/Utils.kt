package com.cristiancizmar.learnalanguage.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.cristiancizmar.learnalanguage.di.RepositoryEntryPoint
import com.cristiancizmar.learnalanguage.presentation.App
import dagger.hilt.android.EntryPointAccessors
import java.io.File

fun shareBackupFile(context: Context) {
    val repositoryEntryPoint = EntryPointAccessors.fromApplication(
        App.appContext!!,
        RepositoryEntryPoint::class.java
    )
    val backupFileName = "learn-a-language-backup.txt"
    context.openFileOutput(backupFileName, Context.MODE_PRIVATE)
        .use {
            it.write(repositoryEntryPoint.getFileWordsRepository().getAllData().toByteArray())
        }
    val file = File(context.filesDir, backupFileName)

    if (file.exists()) {
        val fileUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    }
}