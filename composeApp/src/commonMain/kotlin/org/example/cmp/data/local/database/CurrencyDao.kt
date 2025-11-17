package org.example.cmp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.cmp.domain.model.CurrencyData

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyList(currency: CurrencyData)

    @Query("SELECT * FROM currency")
    fun getAllItems(): Flow<List<CurrencyData>>

    @Query("DELETE FROM currency")
    suspend fun clearUp()
}