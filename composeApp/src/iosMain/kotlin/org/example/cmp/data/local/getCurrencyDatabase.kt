package org.example.cmp.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.cmp.data.local.database.CurrencyDataBase
import platform.Foundation.NSHomeDirectory

fun dataBaseProvider(): CurrencyDataBase{
    val dbFile = NSHomeDirectory() + "/currency.db"
    return Room.databaseBuilder<CurrencyDataBase>(
        name = dbFile,
        factory = { CurrencyDataBase::class.instantiateImpl()}
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}