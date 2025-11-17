package org.example.cmp.data.di

import org.example.cmp.data.local.cache.PreferenceImpl
import org.example.cmp.data.remote.api.CurrencyApi
import org.example.cmp.data.repository.CurrencyRepositoryImpl
import org.example.cmp.domain.repository.CurrencyRepository
import org.example.cmp.domain.repository.Preferences
import org.koin.dsl.module

val dataModules = module {

    /** API Service */
    single { CurrencyApi }

    /** Preferences (needs DataStore injected from platform module) */
    single<Preferences> { PreferenceImpl(prefs = get()) }

    /** Repository */
    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            apiService = get(),
            preferences = get()
        )
    }

}