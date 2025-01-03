package com.cristiancizmar.learnalanguage.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.cristiancizmar.learnalanguage.data.FileWordsRepository
import java.io.File

fun shareBackupFile(context: Context) {
    val backupFileName = "learn-a-language-backup.txt"
    context.openFileOutput(backupFileName, Context.MODE_PRIVATE)
        .use {
            it.write(FileWordsRepository.getAllData().toByteArray())
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