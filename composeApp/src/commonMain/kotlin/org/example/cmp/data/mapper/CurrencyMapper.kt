package org.example.cmp.data.mapper

import org.example.cmp.data.model.CurrencyDetailDTO
import org.example.cmp.data.model.CurrencyResponseDTO
import org.example.cmp.data.model.MetaDTO
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.model.CurrencyResponse
import org.example.cmp.domain.model.Meta

// In CurrencyMapper.kt (Data Layer)
fun MetaDTO.toMeta() = Meta(
    lastUpdatedAt = lastUpdatedAt.orEmpty()
)

fun CurrencyDetailDTO.toCurrencyData() = CurrencyData(
    code = code.orEmpty(),
    value = this.value?.toString().orEmpty() // Still using the same logic
)

fun CurrencyResponseDTO.toCurrencyResponse() = CurrencyResponse(
    meta = meta?.toMeta(), // Safely map the meta field
    data = data.orEmpty() // Safely handle the map: if null, use an empty map
        .filterValues { it != null } // Filter out any null CurrencyDetailDTOs in the map
        .mapValues { (_, dto) -> dto!!.toCurrencyData() } // Map the remaining non-null DTOs
)