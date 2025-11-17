package org.example.cmp.presentation.features.homeScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.example.cmp.domain.utils.ApiResult
import org.example.cmp.data.local.database.CurrencyDao
import org.example.cmp.domain.repository.Preferences
import org.example.cmp.domain.model.CurrencyData
import org.example.cmp.domain.model.RateStatus
import org.example.cmp.domain.usecase.GetLatestExchangeRatesUseCase
import kotlin.time.ExperimentalTime

sealed class HomeUiEvent{
    data object RefreshRates: HomeUiEvent()
    data object SwitchCurrencies: HomeUiEvent()
    data class SaveSourceCurrencyCode(val  code: String): HomeUiEvent()
    data class SaveTargetCurrencyCode(val  code: String): HomeUiEvent()
}

class CurrencyViewModel(
    val currencyUseCase: GetLatestExchangeRatesUseCase,
    private val preferences: Preferences,
    private val currencyDao : CurrencyDao
) : ViewModel() {

    private val _currencyState = mutableStateOf(RateStatus.Idle)
    val currencyState: State<RateStatus> = _currencyState


    private val _sourceCurrency = mutableStateOf<ApiResult<CurrencyData>>(ApiResult.Loading)
    val sourceCurrency: State<ApiResult<CurrencyData>> = _sourceCurrency

    private val _targetCurrency = mutableStateOf<ApiResult<CurrencyData>>(ApiResult.Loading)
    val targetCurrency: State<ApiResult<CurrencyData>> = _targetCurrency

    private var _allCurrencies = mutableStateListOf<CurrencyData>()
    val allCurrencies: List<CurrencyData> = _allCurrencies

    init {
        loadExchangeRates()
    }

     @OptIn(ExperimentalTime::class)
     fun loadExchangeRates() = viewModelScope.launch(Dispatchers.IO){
         val localCache = currencyDao.getAllItems().first()
         if(localCache.isNotEmpty()){
             _allCurrencies.clear()
             _allCurrencies.addAll(localCache)

             if (!preferences.isDataFresh(kotlin.time.Clock.System.now().toEpochMilliseconds())) {
                 println("HomeViewModel: DATA NOT FRESH")
                 cacheTheData()
             } else {
                 println("HomeViewModel: DATA IS FRESH")
                 readSourceCurrency()
                 readTargetCurrency()
             }
         }else{
             cacheTheData()
         }
         geRateStatus()
     }

    @OptIn(ExperimentalTime::class)
    private suspend fun geRateStatus() {
        _currencyState.value = if(preferences.isDataFresh(
                currentTimestamp = kotlin.time.Clock.System.now().toEpochMilliseconds()
            )) RateStatus.Fresh
        else RateStatus.Stale
    }

    fun sendEvent(event: HomeUiEvent) {
        when(event){
            HomeUiEvent.RefreshRates -> {
                loadExchangeRates()
            }
            HomeUiEvent.SwitchCurrencies -> {
                switchCurrencies()
            }

            is HomeUiEvent.SaveTargetCurrencyCode -> {
                saveTargetCurrencyCode(event.code)
            }

            is HomeUiEvent.SaveSourceCurrencyCode -> {
                saveSourceCurrencyCode(event.code)
            }
        }
    }

    private fun readSourceCurrency() = viewModelScope.launch(Dispatchers.IO){
       preferences.readSourceCurrencyCode().collectLatest {  currencyCode ->
           val selectedCurrency = _allCurrencies.find { it.code ==  currencyCode.name}

           withContext(Dispatchers.Main) {
               _sourceCurrency.value = if (selectedCurrency != null) {
                   ApiResult.Success(data = selectedCurrency)
               } else {
                   ApiResult.Error("Couldn't find the selected currency.")
               }
           }
       }

    }

    private fun readTargetCurrency() = viewModelScope.launch(Dispatchers.IO){
        preferences.readTargetCurrencyCode().collectLatest { currencyCode ->
            val selectedCurrency = _allCurrencies.find { it.code ==  currencyCode.name}
            withContext(Dispatchers.Main) {
                _targetCurrency.value = if (selectedCurrency != null) {
                    ApiResult.Success(data = selectedCurrency)
                } else {
                    ApiResult.Error("Couldn't find the selected currency.")
                }
            }
        }
    }

    private suspend fun cacheTheData(){
        val fetchedData = currencyUseCase.invoke()
        if(fetchedData.isSuccess()){
            currencyDao.clearUp()
            fetchedData.getSuccessData().forEach {
                currencyDao.insertCurrencyList(it)
            }
            _allCurrencies.clear()
            _allCurrencies.addAll(fetchedData.getSuccessData())

            readSourceCurrency()
            readTargetCurrency()
        }else if(fetchedData.isError()){
            println("HomeViewModel: FETCHING FAILED ${fetchedData.getErrorMsg()}")
        }
    }

    private fun switchCurrencies(){
        val source = _sourceCurrency.value
        val target = _targetCurrency.value
        _sourceCurrency.value = target
        _targetCurrency.value = source
    }


    private fun saveSourceCurrencyCode(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferences.saveSourceCurrencyCode(code)
        }
    }

    private fun saveTargetCurrencyCode(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferences.saveTargetCurrencyCode(code)
        }
    }

}