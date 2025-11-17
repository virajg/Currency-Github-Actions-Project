package org.example.cmp.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.CreationExtras.Empty.get
import org.example.cmp.data.local.cache.PreferenceImpl
import org.example.cmp.data.local.database.CurrencyDao
import org.example.cmp.data.remote.api.CurrencyApi
import org.example.cmp.data.repository.CurrencyRepositoryImpl
import org.example.cmp.domain.usecase.GetLatestExchangeRatesUseCase
import org.example.cmp.presentation.features.homeScreen.HomeScreen
import org.example.cmp.ui.theme.darkScheme
import org.example.cmp.ui.theme.lightScheme
import org.example.cmp.presentation.features.homeScreen.CurrencyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val colorScheme = if(!isSystemInDarkTheme()) lightScheme else darkScheme
    val viewModel: CurrencyViewModel = koinViewModel()
    HomeScreen(viewModel)
}