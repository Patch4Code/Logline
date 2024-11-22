package com.patch4code.loglinemovieapp.features.core.presentation.utils
/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Back4AppCredentials - Helper class containing credentials and the server URL for Back4App
 *
 * @author Patch4Code
 */
class Back4AppCredentials {
    companion object {
        private const val ENC_BACK4APP_APP_ID = "emZKWFdrQnZSR2R5czYzWDAySDhaQjFMN1c4dDlhTzZIVXByVldRQw=="
        private const val ENC_BACK4APP_CLIENT_KEY = "WFRIdVNGckRhdG1IYmNrV2ttYUxmclc3NzNMYUtobXhZMVdkam9weg=="
        const val BACK4APP_SERVER_URL = "https://parseapi.back4app.com/"
        fun getAppId(): String {
            return Decoder.decodeKey(ENC_BACK4APP_APP_ID)
        }
        fun getClientKey(): String {
            return Decoder.decodeKey(ENC_BACK4APP_CLIENT_KEY)
        }
    }
}