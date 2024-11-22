package com.patch4code.loglinemovieapp.features.core.presentation.utils

import java.util.Base64

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Decoder - Utility object for decoding Base64 encoded string
 *
 * @author Patch4Code
 */
object Decoder {
    fun decodeKey(encodedKey: String): String {
        val decodedBytes = Base64.getDecoder().decode(encodedKey)
        return String(decodedBytes)
    }
}