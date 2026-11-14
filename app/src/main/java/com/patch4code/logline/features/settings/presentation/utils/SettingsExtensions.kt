package com.patch4code.logline.features.settings.presentation.utils

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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.ZonedDateTime
import com.patch4code.logline.room_database.LoglineDatabase
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.diary.domain.model.LoggedMovie
import com.patch4code.logline.api.RetrofitHelper
import com.patch4code.logline.api.TmdbApiService
import com.patch4code.logline.features.core.presentation.utils.TmdbCredentials

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
    fun exportDatabaseFile(context: Context): Boolean {
         return try {
            // Defines the directory to save the zip file (downloads directory of the device)
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            // Saves the database folder and a list of files in the database folder
            val dbFolder = File(context.applicationInfo.dataDir, "databases")
            val dbFiles = dbFolder.listFiles() ?: arrayOf()

            val profileImagePath = File(context.filesDir, "profile_image.jpg")
            val bannerImagePath = File(context.filesDir, "banner_image.jpg")


            // Creates the zip file with a timestamp in its name
            val zipFile = File(downloadsDir, "logline_backup_${getCurrentDateTime()}.zip")

            // Write the files to the zip file with the correct folder structure
            ZipOutputStream(FileOutputStream(zipFile)).use { zipOutputStream ->
                for (file in dbFiles) {
                    val entryName = "db/${file.name}"
                    val entry = ZipEntry(entryName)
                    zipOutputStream.putNextEntry(entry)

                    val input = file.inputStream()
                    input.copyTo(zipOutputStream, DEFAULT_BUFFER_SIZE)
                    input.close()
                    zipOutputStream.closeEntry()
                }
                // Add profile image to "img" folder in the ZIP
                if (profileImagePath.exists()) {
                    val entryName = "img/${profileImagePath.name}"
                    val entry = ZipEntry(entryName)
                    zipOutputStream.putNextEntry(entry)

                    val input = profileImagePath.inputStream()
                    input.copyTo(zipOutputStream, DEFAULT_BUFFER_SIZE)
                    input.close()
                    zipOutputStream.closeEntry()
                }
                // Add banner image to "img" folder in the ZIP
                if (bannerImagePath.exists()) {
                    val entryName = "img/${bannerImagePath.name}"
                    val entry = ZipEntry(entryName)
                    zipOutputStream.putNextEntry(entry)

                    val input = bannerImagePath.inputStream()
                    input.copyTo(zipOutputStream, DEFAULT_BUFFER_SIZE)
                    input.close()
                    zipOutputStream.closeEntry()
                }

                // verify that the exported file exists
                zipFile.exists() && zipFile.length() > 0L
            }
        } catch (e: Exception) {
            Log.e("SettingsHelper", "Export Error: ", e)
            false
        }
    }

    // Import a database zip file
    fun importDatabaseFile(context: Context, importZipFileUri: Uri, onImportSuccess:()->Unit, onImportError:(errorMsg: String)->Unit){

        try {
            // creates inputStream to read the zip file (at the given uri) and
            // therefrom a zipInputStream to read the files within the zip file
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(importZipFileUri)
            val zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

            // Byte-Array-Buffer to read and write the files
            val buffer = ByteArray(1024)

            // Directories for database and images
            val databaseDir = File(context.dataDir, "databases")
            val filesDir = context.filesDir

            // Flag to check structure validity
            var hasDbFolder = false

            // Iterates over all files within the zip file and saves them to the databaseDir
            var zipEntry: ZipEntry? = zipInputStream.nextEntry
            while (zipEntry != null) {
                val entryName = zipEntry.name

                // Check if entry is part of "db/" folder
                if (entryName.startsWith("db/")) {
                    hasDbFolder = true
                    val dbFileName = entryName.removePrefix("db/")
                    Log.e("importDatabaseFile", "dbFileName: $dbFileName")
                    if (dbFileName.isNotEmpty()) {
                        val outputFile = File(databaseDir, dbFileName)
                        outputFile.outputStream().use { fileOutputStream ->
                            var length: Int
                            while (zipInputStream.read(buffer).also { length = it } > 0) {
                                fileOutputStream.write(buffer, 0, length)
                            }
                        }
                    }
                }

                // Check if entry is part of "img/" folder
                if (entryName.startsWith("img/")) {
                    val imgFileName = entryName.removePrefix("img/")
                    if (imgFileName.isNotEmpty()) {
                        val outputFile = File(filesDir, imgFileName)
                        outputFile.outputStream().use { fileOutputStream ->
                            var length: Int
                            while (zipInputStream.read(buffer).also { length = it } > 0) {
                                fileOutputStream.write(buffer, 0, length)
                            }
                        }
                    }
                }

                zipInputStream.closeEntry()
                zipEntry = zipInputStream.nextEntry
            }
            zipInputStream.close()

            if (!hasDbFolder) {
                throw Exception("Invalid ZIP structure: Required folder 'db/' missing.")
            }

            Log.d("SettingsHelper", "Import successful")
            onImportSuccess()

        } catch (e: Exception) {
            onImportError("Import Error: ${e.message}")
            Log.e("SettingsHelper", "Import Error: ", e)
        }
    }

    suspend fun importTmdbCsv(
        context: Context,
        importCsvFileUri: Uri,
        db: LoglineDatabase,
        onImportSuccess: () -> Unit,
        onImportError: (errorMsg: String) -> Unit
    ) {
        try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(importCsvFileUri) ?: throw Exception("Cannot open file")
            val reader = BufferedReader(InputStreamReader(inputStream))

            val tmdbApiService = RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(
                TmdbApiService::class.java
            )

            val header = reader.readLine()
            if (header == null) {
                onImportError("Empty file")
                return
            }

            var line: String? = reader.readLine()
            while (line != null) {
                val tokens = parseCsvLine(line)
                if (tokens.size >= 10 && tokens[2] == "movie") {
                    val tmdbId = tokens[0].toIntOrNull()
                    val title = tokens[3]
                    val releaseDate = tokens[4]
                    val ratingStr = tokens[7]
                    val userRatingStr = tokens[8]
                    val dateRatedStr = tokens[9]

                    if (tmdbId != null) {
                        val voteAverage = ratingStr.toDoubleOrNull() ?: 0.0
                        var movie = Movie(
                            id = tmdbId,
                            title = title,
                            releaseDate = releaseDate.split("T").firstOrNull() ?: releaseDate,
                            voteAverage = voteAverage
                        )
                        val userRating = userRatingStr.toDoubleOrNull()?.toInt() ?: -1

                        try {
                            val response = tmdbApiService.getMovieDetails(tmdbId)
                            if (response.isSuccessful) {
                                response.body()?.let { details ->
                                    movie = movie.copy(posterUrl = details.posterPath)
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("ImportTmdb", "Error fetching movie details for tmdbId $tmdbId", e)
                        }

                        if (userRating != -1) {
                            db.movieUserDataDao.updateOrInsertRating(movie, userRating)
                            if (dateRatedStr.isNotBlank()) {
                                val dateRated = ZonedDateTime.parse(dateRatedStr).toLocalDateTime()
                                val loggedElement = LoggedMovie(
                                    movieId = tmdbId,
                                    date = dateRated,
                                    rating = userRating,
                                    review = ""
                                )
                                db.loggedMovieDao.addNewMovieLog(loggedElement, movie)
                            }
                        } else {
                            db.movieUserDataDao.updateOrInsertOnWatchlist(movie, true)
                        }
                    }
                }
                line = reader.readLine()
            }
            reader.close()
            onImportSuccess()
        } catch (e: Exception) {
            onImportError(e.message ?: "Unknown error")
        }
    }

    private fun parseCsvLine(line: String): List<String> {
        val tokens = mutableListOf<String>()
        val currentToken = StringBuilder()
        var inQuotes = false
        for (char in line) {
            if (char == '\"') {
                inQuotes = !inQuotes
            } else if (char == ',' && !inQuotes) {
                tokens.add(currentToken.toString().trim('\"'))
                currentToken.clear()
            } else {
                currentToken.append(char)
            }
        }
        tokens.add(currentToken.toString().trim('\"'))
        return tokens
    }
}
