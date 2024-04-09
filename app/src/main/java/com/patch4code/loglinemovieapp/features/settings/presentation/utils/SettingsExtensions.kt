package com.patch4code.loglinemovieapp.features.settings.presentation.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
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
            Log.e("SettingsHelper", "Export Error: ", e)
        }
    }

    fun importDatabaseFile(context: Context, importZipFileUri: Uri, onImportSuccess:()->Unit){

        try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(importZipFileUri)
            val zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

            val buffer = ByteArray(1024)

            val databaseDir = File(context.dataDir, "databases")

            var zipEntry: ZipEntry? = zipInputStream.nextEntry
            while (zipEntry != null) {
                val fileName = zipEntry.name
                val outputFile = File(databaseDir, fileName)
                val fileOutputStream = FileOutputStream(outputFile)
                var length: Int
                while (zipInputStream.read(buffer).also { length = it } > 0) {
                    fileOutputStream.write(buffer, 0, length)
                }
                fileOutputStream.close()
                zipInputStream.closeEntry()
                zipEntry = zipInputStream.nextEntry
            }
            zipInputStream.close()

            Log.d("SettingsHelper", "Import successful")
            onImportSuccess()

        } catch (e: Exception) {
            Log.e("SettingsHelper", "Import Error: ", e)
        }
    }
}