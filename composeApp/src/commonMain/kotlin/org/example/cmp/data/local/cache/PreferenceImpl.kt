package org.example.cmp.data.local.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.cmp.domain.model.CurrencyCode
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class PreferenceImpl(
    val prefs: DataStore<Preferences>
) : org.example.cmp.domain.repository.Preferences {


    companion object{
        const val TIMESTAMP_KEY = "lastUpdated"
        const val SOURCE_CURRENCY_KEY = "sourceCurrency"
        const val TARGET_CURRENCY_KEY = "targetCurrency"


        val DEFAULT_SOURCE_CURRENCY = CurrencyCode.USD.name
        val DEFAULT_TARGET_CURRENCY = CurrencyCode.EUR.name
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun saveLastUpdate(lastUpdate: String) {
        prefs.edit { dataStore ->
            val key = longPreferencesKey(TIMESTAMP_KEY)
            dataStore[key] = Instant.parse(lastUpdate).toEpochMilliseconds()
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun isDataFresh(currentTimestamp: Long): Boolean {
       val preferences = prefs.data.first()
        val key = longPreferencesKey(TIMESTAMP_KEY)
        val savedLastUpdate = preferences[key] ?: 0L

      return if(savedLastUpdate != 0L){
            val currentInstant = kotlinx.datetime.Instant.fromEpochMilliseconds(currentTimestamp)
            val savedInstant = kotlinx.datetime.Instant.fromEpochMilliseconds(savedLastUpdate)

            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.Companion.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.Companion.currentSystemDefault())

            val dayDifferences = currentDateTime.date.dayOfMonth - savedDateTime.date.dayOfMonth

            dayDifferences < 1
        }else false
    }

    override suspend fun saveSourceCurrencyCode(code: String) {
        prefs.edit { dataStore ->
            val key = stringPreferencesKey(SOURCE_CURRENCY_KEY)
            dataStore[key] = code
        }
    }

    override suspend fun saveTargetCurrencyCode(code: String) {
        prefs.edit { dataStore ->
            val key = stringPreferencesKey(TARGET_CURRENCY_KEY)
            dataStore[key] = code
        }
    }

    override suspend fun readSourceCurrencyCode(): Flow<CurrencyCode> {
        val key = stringPreferencesKey(SOURCE_CURRENCY_KEY)
        return prefs.data
            .map { preferences ->
                val savedSourceCurrencyCode = preferences[key] ?: DEFAULT_SOURCE_CURRENCY
                CurrencyCode.valueOf(savedSourceCurrencyCode)
            }
    }

    override suspend fun readTargetCurrencyCode(): Flow<CurrencyCode> {
        val key = stringPreferencesKey(TARGET_CURRENCY_KEY)
        return prefs.data
            .map { preferences ->
                val savedTargetCurrencyCode = preferences[key] ?: DEFAULT_TARGET_CURRENCY
                CurrencyCode.valueOf(savedTargetCurrencyCode)
            }
    }
}