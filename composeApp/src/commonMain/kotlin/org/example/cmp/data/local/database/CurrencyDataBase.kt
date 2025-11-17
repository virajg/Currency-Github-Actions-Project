package org.example.cmp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.cmp.domain.model.CurrencyData

@Database(
    entities = [CurrencyData::class],
    version = 1
)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}