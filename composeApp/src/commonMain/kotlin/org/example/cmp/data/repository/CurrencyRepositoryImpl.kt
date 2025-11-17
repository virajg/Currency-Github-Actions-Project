package org.example.cmp.data.repository

import org.example.cmp.data.mapper.toCurrencyResponse
import org.example.cmp.data.remote.api.CurrencyApi
import org.example.cmp.domain.mapper.toMapper
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.repository.CurrencyRepository
import org.example.cmp.domain.repository.Preferences
import org.example.cmp.domain.utils.ApiResult

class CurrencyRepositoryImpl(
    private val apiService: CurrencyApi,
    private val preferences: Preferences
) : CurrencyRepository {

    override suspend fun getLastestExchangeRates(): ApiResult<List<CurrencyData>> {

        // 1. Call the API, expecting the top-level wrapper object (CurrencyDataResponse)
        val apiResult = apiService.getLastedRates()

        // 2. Unwrap the ApiResult and transform the successful data
        val response = when (apiResult) {
            is ApiResult.Success -> {

                val currencyList = apiResult.data.toCurrencyResponse().toMapper()

                val lastUpdated = apiResult.data.meta?.lastUpdatedAt.orEmpty()
                preferences.saveLastUpdate(lastUpdated)

                ApiResult.Success(currencyList)
            }

            is ApiResult.Error -> apiResult // ✅ Works now (type-safe)
            is ApiResult.Loading -> ApiResult.Loading
        }

        return response
    }
}