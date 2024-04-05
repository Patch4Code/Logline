package com.patch4code.loglinemovieapp.features.profile.presentation.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

object ProfileEditExtensions {

    fun saveProfileImageToStorage(context: Context, uri: Uri): Uri? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream: OutputStream
        var file: File? = null
        try {
            // Save the image to the app's internal memory
            file = File(context.filesDir, "profile_image.jpg")
            outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //Log.e("saveProfileImageToStorage", "uri: ${file?.toUri()}")
        return file?.toUri()
    }

    fun saveBannerImageToStorage(context: Context, uri: Uri): Uri? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream: OutputStream
        var file: File? = null
        try {
            // Save the image to the app's internal memory
            file = File(context.filesDir, "banner_image.jpg")
            outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file?.toUri()
    }

}