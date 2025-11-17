package org.example.cmp.domain.repository

import org.example.cmp.domain.utils.ApiResult
import org.example.cmp.domain.model.CurrencyData

interface CurrencyRepository {
    suspend fun getLastestExchangeRates() : ApiResult<List<CurrencyData>>
}