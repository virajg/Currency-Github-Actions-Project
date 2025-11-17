package org.example.cmp.domain.usecase

import org.example.cmp.domain.utils.ApiResult
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.repository.CurrencyRepository

class GetLatestExchangeRatesUseCase(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): ApiResult<List<CurrencyData>> {
        return repository.getLastestExchangeRates()
    }
}