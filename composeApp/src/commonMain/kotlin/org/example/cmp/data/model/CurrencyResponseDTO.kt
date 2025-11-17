package org.example.cmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.model.Meta

@Serializable
data class CurrencyResponseDTO(
    val meta : MetaDTO? = null,
    val data: Map<String,CurrencyDetailDTO?>? = null
)

@Serializable
data class MetaDTO(
    @SerialName("last_updated_at")
    val lastUpdatedAt : String? = null
){
    fun MetaDTO.toMeta() = Meta(
        lastUpdatedAt = lastUpdatedAt.orEmpty()
    )
}

@Serializable
data class CurrencyDetailDTO(
    val code : String? = null,
    val value : Double? = null
){
    fun CurrencyDetailDTO.toCurrencyData() = CurrencyData(
        code = code.orEmpty(),
        value = value?.toString().orEmpty()
    )
}

