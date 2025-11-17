package org.example.cmp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.cmp.data.local.dataBaseProvider
import org.example.cmp.data.local.dataStoreProvider
import org.example.cmp.data.local.database.CurrencyDataBase
import org.koin.dsl.module

val iosPlatformModule = module {
    single<CurrencyDataBase> { dataBaseProvider() }
    single { get<CurrencyDataBase>().currencyDao() }

    single<DataStore<Preferences>> { dataStoreProvider() }
}