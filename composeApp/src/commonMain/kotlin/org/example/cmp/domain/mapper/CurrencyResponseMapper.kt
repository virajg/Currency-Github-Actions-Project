package org.example.cmp.domain.mapper

import org.example.cmp.domain.model.CurrencyCode
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.model.CurrencyResponse

fun CurrencyResponse.toMapper() : List<CurrencyData>{

    val availableDomainCodes = CurrencyCode.entries
        .map { code -> code.name }
        .toSet()

    return data
        .values
        .filter { currency ->
            // 1. Ensure the currency code exists in our allowed Domain enums
            availableDomainCodes.contains(currency.code)
        }
        .map { currency ->
            // Use mapNotNull to safely convert and skip if data is missing
            CurrencyData(
                code = currency.code, // Directly use the string code
                value = currency.value
            )
        }
}