package org.example.cmp.domain.di

import org.example.cmp.domain.usecase.GetLatestExchangeRatesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetLatestExchangeRatesUseCase(
            repository = get()
        )
    }
}