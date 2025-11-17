package org.example.cmp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val meta : Meta?,
    val data: Map<String,CurrencyData>
)

@Serializable
data class Meta(
    @SerialName("last_updated_at")
    val lastUpdatedAt : String
)

@Entity(tableName = "currency")
@Serializable
data class CurrencyData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code : String,
    val value : String
)

