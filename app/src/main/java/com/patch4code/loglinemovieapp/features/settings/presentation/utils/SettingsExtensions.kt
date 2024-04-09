package com.patch4code.loglinemovieapp.features.settings.presentation.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object SettingsExtensions {

    private fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
        return currentDateTime.format(formatter)
    }

    fun exportDatabaseFile(context: Context) {
        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            val dbFolder = File(context.applicationInfo.dataDir, "databases")
            val filesToZip = dbFolder.listFiles() ?: arrayOf()

            val zipFile = File(downloadsDir, "logline_backup_${getCurrentDateTime()}.zip")

            ZipOutputStream(FileOutputStream(zipFile)).use { zipOutputStream ->
                for (file in filesToZip) {
                    val entryName = file.name
                    val entry = ZipEntry(entryName)
                    zipOutputStream.putNextEntry(entry)

                    val input = file.inputStream()
                    input.copyTo(zipOutputStream, DEFAULT_BUFFER_SIZE)
                    input.close()
                    zipOutputStream.closeEntry()
                }
            }
        } catch (e: Exception) {
            Log.e("SettingsHelper", "Error: ", e)
        }
    }

}