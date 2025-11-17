package org.example.cmp.presentation.features.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.example.cmp.domain.model.CurrencyType
import org.example.cmp.presentation.components.CurrencyPickerDialog
import org.example.cmp.presentation.components.HomeBody
import org.example.cmp.presentation.components.HomeHeader
import org.example.cmp.ui.theme.surfaceLight

@Composable
fun HomeScreen(
    viewModel: CurrencyViewModel
) {
    val rateStatus by viewModel.currencyState
    val allCurrencies = viewModel.allCurrencies
    val source by viewModel.sourceCurrency
    val target by viewModel.targetCurrency

    var amount by rememberSaveable { mutableStateOf(0.0) }

    var selectedCurrencyType : CurrencyType by remember {
        mutableStateOf(CurrencyType.None)
    }

    var dialogOpened by remember { mutableStateOf(true) }

    if(dialogOpened && selectedCurrencyType != CurrencyType.None){
        CurrencyPickerDialog(
            currencies = allCurrencies,
            currencyType = selectedCurrencyType,
            onConfirmClick = { code ->

                if(selectedCurrencyType is CurrencyType.Source){
                    viewModel.sendEvent(
                        HomeUiEvent.SaveSourceCurrencyCode(code.name)
                    )
                }else {
                    viewModel.sendEvent(
                        HomeUiEvent.SaveTargetCurrencyCode(code.name)
                    )
                }

                selectedCurrencyType = CurrencyType.None
                dialogOpened = false
            },
            onDismiss = {
                selectedCurrencyType = CurrencyType.None
                dialogOpened = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceLight)
    ) {
        HomeHeader(
            status = rateStatus,
            onRatesRefresh = {
                viewModel.sendEvent(
                    HomeUiEvent.RefreshRates
                )
            },
            onSwitchClick = {
                viewModel.sendEvent(
                    HomeUiEvent.SwitchCurrencies
                )
            },
            onCurrencyTypeSelect = { currencyType ->
                selectedCurrencyType = currencyType
                dialogOpened = true
            },
            source = source,
            target = target,
            amount = amount,
            onAmountChanged = {amount = it}
        )
        HomeBody(
            source = source,
            target = target,
            amount = amount
        )
    }
}