package org.example.cmp.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.cmp.data.local.database.CurrencyDataBase

fun dataBaseProvider(context: Context): CurrencyDataBase {
    val dbFile = context.getDatabasePath("currency.db")

    return Room.databaseBuilder<CurrencyDataBase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}