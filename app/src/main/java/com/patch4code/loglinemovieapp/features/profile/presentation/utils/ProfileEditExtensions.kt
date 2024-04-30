package com.patch4code.loglinemovieapp.features.profile.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileEditExtensions - Provides function to save a image to the internal memory
 *
 * @author Patch4Code
 */
object ProfileEditExtensions {

    fun saveImageToStorage(context: Context, uri: Uri, fileName: String): Uri? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream: OutputStream
        var file: File? = null
        try { // Save the image to the app's internal memory

            file = File(context.filesDir, fileName)
            outputStream = FileOutputStream(file)

            val imageSizeInBytes = inputStream?.available() ?: 0

            // Determine the compression quality based on image size
            //not used for now
            val quality = when {
                imageSizeInBytes > 0.5 * 1024 * 1024 -> 60 //larger than 0.5MB
                imageSizeInBytes > 0.25 * 1024 * 1024 -> 70 //larger than 0.25MB
                imageSizeInBytes > 0.1 * 1024 * 1024 -> 80 //larger than 0.1MB
                else -> 90 // Otherwise, use higher quality compression
            }

            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file?.toUri()
    }
}