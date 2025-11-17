package org.example.cmp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.cmp.data.local.dataBaseProvider
import org.example.cmp.data.local.dataStoreProvider
import org.example.cmp.data.local.database.CurrencyDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    // register Android Context for Koin (startKoin { androidContext(this@App) })
    single<CurrencyDataBase> {
        dataBaseProvider(androidContext())    // call a local function (below)
    }

    // register DAO after DB
    single { get<CurrencyDataBase>().currencyDao() }


    single<DataStore<Preferences>> { dataStoreProvider(androidContext()) }
}