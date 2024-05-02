package com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.settings.domain.model.Country
import com.patch4code.loglinemovieapp.preferences_datastore.StoreSettings
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SettingsViewModel - ViewModel for managing settings data.
 *
 * @author Patch4Code
 */
class SettingsViewModel: ViewModel() {

    private lateinit var settingsDataStore: StoreSettings

    // List of countries with their country code, name, and flag code
    private val countries = listOf(
        Country("AE", "United Arab Emirates", "\uD83C\uDDE6\uD83C\uDDEA"),
        Country("AL", "Albania", "\uD83C\uDDE6\uD83C\uDDF1"),
        Country("AR", "Argentina", "\uD83C\uDDE6\uD83C\uDDF7"),
        Country("AT", "Austria", "\uD83C\uDDE6\uD83C\uDDF9"),
        Country("AU", "Australia", "\uD83C\uDDE6\uD83C\uDDFA"),
        Country("BA", "Bosnia and Herzegovina", "\uD83C\uDDE7\uD83C\uDDE6"),
        Country("BB", "Barbados", "\uD83C\uDDE7\uD83C\uDDE7"),
        Country("BE", "Belgium", "\uD83C\uDDE7\uD83C\uDDEA"),
        Country("BG", "Bulgaria", "\uD83C\uDDE7\uD83C\uDDEC"),
        Country("BH", "Bahrain", "\uD83C\uDDE7\uD83C\uDDED"),
        Country("BO", "Bolivia", "\uD83C\uDDE7\uD83C\uDDF4"),
        Country("BR", "Brazil", "\uD83C\uDDE7\uD83C\uDDF7"),
        Country("BS", "Bahamas", "\uD83C\uDDE7\uD83C\uDDF8"),
        Country("CA", "Canada", "\uD83C\uDDE8\uD83C\uDDE6"),
        Country("CH", "Switzerland", "\uD83C\uDDE8\uD83C\uDDED"),
        Country("CL", "Chile", "\uD83C\uDDE8\uD83C\uDDF1"),
        Country("CO", "Colombia", "\uD83C\uDDE8\uD83C\uDDF4"),
        Country("CR", "Costa Rica", "\uD83C\uDDE8\uD83C\uDDF7"),
        Country("CV", "Cape Verde", "\uD83C\uDDE8\uD83C\uDDFB"),
        Country("CZ", "Czech Republic", "\uD83C\uDDE8\uD83C\uDDFF"),
        Country("DE", "Germany", "\uD83C\uDDE9\uD83C\uDDEA"),
        Country("DK", "Denmark", "\uD83C\uDDE9\uD83C\uDDF0"),
        Country("DO", "Dominican Republic", "\uD83C\uDDE9\uD83C\uDDF4"),
        Country("EC", "Ecuador", "\uD83C\uDDEA\uD83C\uDDE8"),
        Country("EE", "Estonia", "\uD83C\uDDEA\uD83C\uDDEA"),
        Country("EG", "Egypt", "\uD83C\uDDEA\uD83C\uDDEC"),
        Country("ES", "Spain", "\uD83C\uDDEA\uD83C\uDDF8"),
        Country("FI", "Finland", "\uD83C\uDDEB\uD83C\uDDEE"),
        Country("FJ", "Fiji", "\uD83C\uDDEB\uD83C\uDDEF"),
        Country("FR", "France", "\uD83C\uDDEB\uD83C\uDDF7"),
        Country("GB", "United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7"),
        Country("GF", "French Guiana", "\uD83C\uDDEC\uD83C\uDDEB"),
        Country("GI", "Gibraltar", "\uD83C\uDDEC\uD83C\uDDEE"),
        Country("GR", "Greece", "\uD83C\uDDEC\uD83C\uDDF7"),
        Country("GT", "Guatemala", "\uD83C\uDDEC\uD83C\uDDF9"),
        Country("HK", "Hong Kong", "\uD83C\uDDED\uD83C\uDDF0"),
        Country("HN", "Honduras", "\uD83C\uDDED\uD83C\uDDF3"),
        Country("HR", "Croatia", "\uD83C\uDDED\uD83C\uDDF7"),
        Country("HU", "Hungary", "\uD83C\uDDED\uD83C\uDDFA"),
        Country("ID", "Indonesia", "\uD83C\uDDEE\uD83C\uDDE9"),
        Country("IE", "Ireland", "\uD83C\uDDEE\uD83C\uDDEA"),
        Country("IL", "Israel", "\uD83C\uDDEE\uD83C\uDDF1"),
        Country("IN", "India", "\uD83C\uDDEE\uD83C\uDDF3"),
        Country("IQ", "Iraq", "\uD83C\uDDEE\uD83C\uDDF6"),
        Country("IS", "Iceland", "\uD83C\uDDEE\uD83C\uDDF8"),
        Country("IT", "Italy", "\uD83C\uDDEE\uD83C\uDDF9"),
        Country("JM", "Jamaica", "\uD83C\uDDEF\uD83C\uDDF2"),
        Country("JO", "Jordan", "\uD83C\uDDEF\uD83C\uDDF4"),
        Country("JP", "Japan", "\uD83C\uDDEF\uD83C\uDDF5"),
        Country("KR", "South Korea", "\uD83C\uDDF0\uD83C\uDDF7"),
        Country("KW", "Kuwait", "\uD83C\uDDF0\uD83C\uDDFC"),
        Country("LB", "Lebanon", "\uD83C\uDDF1\uD83C\uDDE7"),
        Country("LI", "Liechtenstein", "\uD83C\uDDF1\uD83C\uDDEE"),
        Country("LT", "Lithuania", "\uD83C\uDDF1\uD83C\uDDF9"),
        Country("LV", "Latvia", "\uD83C\uDDF1\uD83C\uDDFB"),
        Country("MD", "Moldova", "\uD83C\uDDF2\uD83C\uDDE9"),
        Country("MK", "North Macedonia", "\uD83C\uDDF2\uD83C\uDDF0"),
        Country("MT", "Malta", "\uD83C\uDDF2\uD83C\uDDF9"),
        Country("MU", "Mauritius", "\uD83C\uDDF2\uD83C\uDDFA"),
        Country("MX", "Mexico", "\uD83C\uDDF2\uD83C\uDDFD"),
        Country("MY", "Malaysia", "\uD83C\uDDF2\uD83C\uDDFE"),
        Country("MZ", "Mozambique", "\uD83C\uDDF2\uD83C\uDDFF"),
        Country("NL", "Netherlands", "\uD83C\uDDF3\uD83C\uDDF1"),
        Country("NO", "Norway", "\uD83C\uDDF3\uD83C\uDDF4"),
        Country("NZ", "New Zealand", "\uD83C\uDDF3\uD83C\uDDFF"),
        Country("OM", "Oman", "\uD83C\uDDF4\uD83C\uDDF2"),
        Country("PA", "Panama", "\uD83C\uDDF5\uD83C\uDDE6"),
        Country("PE", "Peru", "\uD83C\uDDF5\uD83C\uDDEA"),
        Country("PH", "Philippines", "\uD83C\uDDF5\uD83C\uDDED"),
        Country("PK", "Pakistan", "\uD83C\uDDF5\uD83C\uDDF0"),
        Country("PL", "Poland", "\uD83C\uDDF5\uD83C\uDDF1"),
        Country("PS", "Palestine", "\ud83c\uddf5\ud83c\uddf8"),
        Country("PT", "Portugal", "\uD83C\uDDF5\uD83C\uDDF9"),
        Country("PY", "Paraguay", "\uD83C\uDDF5\uD83C\uDDFE"),
        Country("QA", "Qatar", "\uD83C\uDDF6\uD83C\uDDE6"),
        Country("RO", "Romania", "\uD83C\uDDF7\uD83C\uDDF4"),
        Country("RS", "Serbia", "\uD83C\uDDF7\uD83C\uDDF8"),
        Country("RU", "Russia", "\uD83C\uDDF7\uD83C\uDDFA"),
        Country("SA", "Saudi Arabia", "\uD83C\uDDF8\uD83C\uDDE6"),
        Country("SE", "Sweden", "\uD83C\uDDF8\uD83C\uDDEA"),
        Country("SG", "Singapore", "\uD83C\uDDF8\uD83C\uDDEC"),
        Country("SI", "Slovenia", "\uD83C\uDDF8\uD83C\uDDEE"),
        Country("SK", "Slovakia", "\uD83C\uDDF8\uD83C\uDDF0"),
        Country("SM", "San Marino", "\uD83C\uDDF8\uD83C\uDDF2"),
        Country("SV", "El Salvador", "\uD83C\uDDF8\uD83C\uDDFB"),
        Country("TH", "Thailand", "\uD83C\uDDF9\uD83C\uDDED"),
        Country("TR", "Turkey", "\uD83C\uDDF9\uD83C\uDDF7"),
        Country("TT", "Trinidad and Tobago", "\uD83C\uDDF9\uD83C\uDDF9"),
        Country("TW", "Taiwan", "\uD83C\uDDF9\uD83C\uDDFC"),
        Country("UG", "Uganda", "\uD83C\uDDFA\uD83C\uDDEC"),
        Country("US", "United States", "\uD83C\uDDFA\uD83C\uDDF8"),
        Country("UY", "Uruguay", "\uD83C\uDDFA\uD83C\uDDFE"),
        Country("VE", "Venezuela", "\uD83C\uDDFB\uD83C\uDDEA"),
        Country("YE", "Yemen", "\uD83C\uDDFE\uD83C\uDDEA"),
        Country("ZA", "South Africa", "\uD83C\uDDFF\uD83C\uDDE6")
    )

    fun initializeSettingsDataStore(context: Context) {
        settingsDataStore = StoreSettings(context)
    }

    fun getCountriesList(): List<Country> {
        return countries
    }

    fun getCountryNameByCode(countryCode: String): String {
        val foundCountry = countries.find { it.countryCode == countryCode }
        return foundCountry?.countryName ?: ""
    }

    fun setCountry(countryCode: String){
        viewModelScope.launch {
            settingsDataStore.setWatchProvidersCountry(countryCode)
        }
    }
}