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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SettingsExtensions - Helper object for exporting and importing database files.
 *
 * @author Patch4Code
 */
object SettingsExtensions {

    // Returns the current date and time in the format "yyyy-MM-dd_HH-mm-ss"
    private fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
        return currentDateTime.format(formatter)
    }

    // Export the database files to a zip file
    fun exportDatabaseFile(context: Context) {
        try {
            // Defines the directory to save the zip file (downloads directory of the device)
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            // Saves the database folder and a list of files in the database folder
            val dbFolder = File(context.applicationInfo.dataDir, "databases")
            val filesToZip = dbFolder.listFiles() ?: arrayOf()

            // Creates the zip file with a timestamp in its name
            val zipFile = File(downloadsDir, "logline_backup_${getCurrentDateTime()}.zip")

            // Write the files from the dbFolder to the zip file
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

    // Import a database zip file
    fun importDatabaseFile(context: Context, importZipFileUri: Uri, onImportSuccess:()->Unit){

        try {
            // creates inputStream to read the zip file (at the given uri) and
            // therefrom a zipInputStream to read the files within the zip file
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(importZipFileUri)
            val zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

            // Byte-Array-Buffer to read and write the files
            val buffer = ByteArray(1024)

            val databaseDir = File(context.dataDir, "databases")

            // Iterates over all files within the zip file and saves them to the databaseDir
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