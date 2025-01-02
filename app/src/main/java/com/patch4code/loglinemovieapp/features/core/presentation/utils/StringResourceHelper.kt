package com.patch4code.loglinemovieapp.features.core.presentation.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * StringResourceHelper - Utility object to dynamically fetch string resources by their names.
 *
 * @author Patch4Code
 */

object StringResourceHelper {
    @SuppressLint("DiscouragedApi")
    fun getStringResourceFromName(context: Context, resourceName: String): String {
        val resId = context.resources.getIdentifier(resourceName, "string", context.packageName)
        return if (resId != 0) {
            context.getString(resId)
        } else {
            "Unknown String Resource"
        }
    }
}