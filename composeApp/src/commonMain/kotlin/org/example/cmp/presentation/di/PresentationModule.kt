package org.example.cmp.presentation.di

import org.example.cmp.presentation.features.homeScreen.CurrencyViewModel
import org.koin.dsl.module

val presentationModule = module {

    factory {
        CurrencyViewModel(
            currencyUseCase = get(),
            preferences = get(),
            currencyDao = get()
        )
    }
}