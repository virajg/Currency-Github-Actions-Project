package org.example.cmp.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.example.cmp.domain.model.CurrencyData

@Database(
    entities = [CurrencyData::class],
    version = 1
)
@ConstructedBy(CurrencyDataBaseConstructor::class)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object CurrencyDataBaseConstructor : RoomDatabaseConstructor<CurrencyDataBase> {
    override fun initialize(): CurrencyDataBase
}
