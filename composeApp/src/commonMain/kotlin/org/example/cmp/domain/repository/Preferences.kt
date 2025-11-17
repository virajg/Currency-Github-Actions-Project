package org.example.cmp.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.cmp.domain.model.CurrencyCode

interface Preferences {
    suspend fun saveLastUpdate(lastUpdate: String)
    suspend fun isDataFresh(currentTimestamp:Long): Boolean
    suspend fun saveSourceCurrencyCode(code: String)
    suspend fun saveTargetCurrencyCode(code: String)
    suspend fun readSourceCurrencyCode(): Flow<CurrencyCode>
    suspend fun readTargetCurrencyCode() : Flow<CurrencyCode>
}